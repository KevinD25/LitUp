package com.example.shado.litup

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.format.Formatter
import android.util.Log
import android.widget.SeekBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_device_change_settings.*
import kotlinx.android.synthetic.main.activity_device_setup.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL

class ChangeSettingsActivity : AppCompatActivity() {
    private val TAG : String = "ChangeSettingsActivity"

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    private var firebaseToken : String? = null
    private lateinit var currentUserInfo : User

    private val service = RetrofitInstance.getRetrofitInstance().create(LitUpDataService::class.java)

    private var deviceIp : String = "192.168.137.100"

    lateinit var settings : Settings
    private var settingsId = 0
    var disposable : Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_change_settings)

        auth = FirebaseAuth.getInstance()

        var extras = intent.extras
        if(extras != null)
            settingsId = (extras["settingsId"] as Number).toInt()

        seekbar.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                lbl_brightnessvalue.text = "$i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        txt_sleep.setOnClickListener{
            val newFragment = TimePickerFragment()
            newFragment.show(fragmentManager, "Time Picker")
        }
        txt_wake.setOnClickListener{
            val newFragment = TimePickerFragment()
            newFragment.show(fragmentManager, "Time Picker")
        }

        btn_test.setOnClickListener {
            doAsync {
                val result = URL("http://" + deviceIp + "/check").readText()
                uiThread { Log.d(TAG, result) }
            }
        }

        btn_save.setOnClickListener{
            var newSettings = Settings()
            var city = txt_changecity.text.toString()
            if(city != null || city.equals(""))
                newSettings.Location = city
            var brightness = lbl_brightnessvalue.text.toString()
            if(brightness != null || brightness.equals(""))
                try{
                    newSettings.Brightness = Integer.valueOf(brightness)
                }catch (e : NumberFormatException){
                    e.printStackTrace()
                }
            var sleepTime = txt_sleep.text.toString()
            if(sleepTime != null || sleepTime.equals(""))
                newSettings.SleepTime = sleepTime
            var wakeTime = txt_wake.text.toString()
            if(wakeTime != null || wakeTime.equals(""))
                newSettings.WakeTime = wakeTime


            disposable = service.updateSettings(settingsId, newSettings, "Bearer " + firebaseToken).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    {result -> fillSettings(result)},
                    {error -> Log.e(TAG, error.message)})

            var param = "sleep=" + sleepTime + "&wake=" + wakeTime + "&city=" + city + "&brightness=" + brightness
            if(emptycheck(city, brightness, sleepTime, wakeTime)) {
                doAsync {
                    val result = URL("http://" + deviceIp + "/changesettings?" + param).readText()
                    uiThread {
                        Log.d("Request", result)
                        //lbl_response.text = result
                        toast(result)
                    }

                }
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Please fill in all the fields!")
                builder.setPositiveButton("Ok"){dialog, which ->}
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    fun fillSettings(settings: Settings){
        this.settings = settings
        txt_sleep.setText(settings.SleepTime)
        txt_wake.setText(settings.WakeTime)
        txt_changecity.setText(settings.Location)
        lbl_brightnessvalue.setText(settings.Brightness.toString())
        seekbar.progress = settings.Brightness.toInt()
    }

    fun emptycheck(city: String, brightness: String, sleep: String, wake: String): Boolean{
        return(city != "" && brightness != "" && sleep != "" && wake != "")
    }

    fun setIpAddress(){
        var wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var ip = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        Log.d("ip address", ip)
        deviceIp = ip.substring(0, ip.lastIndexOf('.') + 1) + "100"
        Log.d("device Ip", deviceIp)
    }

    override fun onStart() {
        super.onStart()
        currentUser = auth.currentUser as FirebaseUser
        var token = currentUser.getIdToken(true)
        token.addOnCompleteListener {
            result ->
            firebaseToken = result.result?.token
            Log.d(TAG, firebaseToken)
            service.getUser("Bearer " + firebaseToken, currentUser.uid).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    {result ->
                        currentUserInfo = result
                        if (settingsId != 0)
                            disposable = service.getSettings("Bearer " + firebaseToken, settingsId).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                    { result -> fillSettings(result) },
                                    { error -> Log.e(TAG, error.message) }
                            )
                    },
                    {error -> Log.e(TAG, error.message)})
        }
        setIpAddress()
    }
}
