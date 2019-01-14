package com.example.shado.litup

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import android.widget.Button
import kotlinx.android.synthetic.main.activity_device_overview.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL
import android.widget.TextView



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

        val btn_save : Button = findViewById(R.id.btn_save)
        btn_save.setOnClickListener {
            saveSettings()
        }
        fillSpinners()
    }

    fun fillSettings(settings: Settings){
        this.settings = settings
        spnHourFrom.value = getTimeAt(settings.WakeTime, 1)
        spnMinuteFrom.value = getTimeAt(settings.WakeTime, 2)
        spnHourTill.value = getTimeAt(settings.SleepTime, 1)
        spnMinuteTill.value = getTimeAt(settings.SleepTime, 2)
        txt_deviceName.setText(settings.DeviceName)
        etxtLocation.setText(settings.Location)
        seekBar.progress = settings.Brightness.toInt()
    }

    fun getTimeAt(time : String, position : Int) : Int {
        var timeInt = 0
        if(position == 1)
            timeInt = time.substring(0, time.indexOf(':')).toInt()
        else
            timeInt = time.substring(time.indexOf(':') + 1).toInt()
        return timeInt
    }

    fun emptycheck(city: String, brightness: String, sleep: String, wake: String): Boolean{
        return(city != "" && brightness != "" && sleep != "" && wake != "")
    }

    private fun saveSettings(){
        var newSettings = Settings()
        var city = etxtLocation.text.toString()
        if(city != null && !city.equals(""))
            newSettings.Location = city
        var brightness = seekBar.progress.toString()
        if(brightness != null && !brightness.equals(""))
            try{
                newSettings.Brightness = Integer.valueOf(brightness)
            }catch (e : NumberFormatException){
                e.printStackTrace()
            }
        var sleepTime = spnHourTill.value.toString() + ":" + spnMinuteTill.value.toString()
        if(sleepTime != null && !sleepTime.equals(""))
            newSettings.SleepTime = sleepTime
        var wakeTime = spnHourFrom.value.toString() + ":" + spnMinuteFrom.value.toString()
        if(wakeTime != null && !wakeTime.equals(""))
            newSettings.WakeTime = wakeTime
        var deviceName = txt_deviceName.text.toString()
        if(deviceName != null && !deviceName.equals(""))
            newSettings.DeviceName = deviceName

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
        spnHourFrom.maxValue = 24
        spnHourTill.maxValue = 24
        spnMinuteFrom.maxValue = 59
        spnMinuteTill.maxValue = 59
    }
}
