package com.example.shado.litup

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.format.Formatter
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.shado.litup.Model.Settings
import com.example.shado.litup.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_device_change_settings.*
import kotlinx.android.synthetic.main.activity_device_overview.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL


@Suppress("DEPRECATION")
class DeviceOverviewActivity : AppCompatActivity() {
    private val TAG : String = "DeviceOverviewActivity"

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    private var firebaseToken : String? = null
    private lateinit var currentUserInfo : User
    lateinit var customView : View
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

        seekBar.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })


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
        spnHourFrom.maxValue = 24
        spnHourTill.maxValue = 24
        spnMinuteFrom.maxValue = 59
        spnMinuteTill.maxValue = 59
    }

    fun createPopUp(view: View, position:Int) {


        // Initialize a new instance of detailpopup window
        val popupWindow = PopupWindow(
                view, // Custom view to show in detailpopup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of detailpopup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )

        // Set an elevation for the detailpopup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Create a new slide animation for detailpopup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for detailpopup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut

        }

        customView = popupWindow.contentView ?: return




        //TODO FILL EDITTEXT


        popupWindow.isFocusable = true

        // Set a dismiss listener for detailpopup window
        popupWindow.setOnDismissListener {
            //Toast.makeText(applicationContext, "Popup closed", Toast.LENGTH_SHORT).show()
            this.setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }

        // Finally, show the detailpopup window on app
       // TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
                root_layout, // Location to display detailpopup window
                Gravity.CENTER, // Exact position of layout to display detailpopup
                0, // X offset
                0 // Y offset
        )
    }
}
