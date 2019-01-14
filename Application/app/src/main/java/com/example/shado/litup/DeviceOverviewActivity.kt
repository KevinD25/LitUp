package com.example.shado.litup

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.shado.litup.Model.Settings
import com.example.shado.litup.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_device_change_settings.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_device_overview.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL

class DeviceOverviewActivity : AppCompatActivity() {
    private val TAG : String = "DeviceOverviewActivity"

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    private var firebaseToken : String? = null
    private lateinit var currentUserInfo : User

    private val service = RetrofitInstance.getRetrofitInstance().create(LitUpDataService::class.java)

    private var deviceIp : String = "192.168.137.100"

    lateinit var settings : Settings
    private var settingsId = 0
    var disposable : Disposable? = null

    var Hours = arrayListOf<Int>()
    var Minutes = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_overview)

        auth = FirebaseAuth.getInstance()

        var extras = intent.extras
        if(extras != null)
            settingsId = (extras["settingsId"] as Number).toInt()

        btnScreensaver.setOnClickListener{
            goToScreensaver()
        }

        seekBar.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                lbl_brightnessvalue.text = "$i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        spnHourFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                spnHourFrom.setSelection(0)
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var id = spnHourFrom.selectedItemId
                Log.d(TAG, id.toString())
            }
        }

        /*btn_test.setOnClickListener {
            doAsync {
                val result = URL("http://" + deviceIp + "/check").readText()
                uiThread { Log.d(TAG, result) }
            }
        }*/

        fillSpinners()
    }

    fun fillSettings(settings: Settings){
        this.settings = settings
        //txt_sleep.setText(settings.SleepTime)
        //txt_wake.setText(settings.WakeTime)
        txt_changecity.setText(settings.Location)
        seekbar.progress = settings.Brightness.toInt()
    }

    fun stringToTime(time : String){
        
    }

    fun emptycheck(city: String, brightness: String, sleep: String, wake: String): Boolean{
        return(city != "" && brightness != "" && sleep != "" && wake != "")
    }

    private fun saveSettings(){
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

    fun setIpAddress(){
        var wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var ip = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        Log.d("ip address", ip)
        deviceIp = ip.substring(0, ip.lastIndexOf('.') + 1) + "100"
        Log.d("device Ip", deviceIp)
    }

    private fun goToScreensaver(){
        val intent = Intent(this, CustomScreenActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun fillSpinners(){
        for (i in 0..23) {
            Hours.add(i)
        }
        for(i in 0..59){
            Minutes.add(i)
        }

        spnHourFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Hours)
        spnHourTill.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Hours)
        spnMinuteFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Minutes)
        spnMinuteTill.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Minutes)

    }
}
