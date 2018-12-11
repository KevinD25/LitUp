package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG : String = "Mainactivity"

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser

    private val service = RetrofitInstance.getRetrofitInstance().create(LitUpDataService::class.java)

    public lateinit var settings : Settings
    var disposable :Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_devicesetup.setOnClickListener{
            val intent = Intent(this, DeviceSetupActivity::class.java)
            startActivity(intent)
        }

        btn_changesettings.setOnClickListener {
            val intent = Intent(this, ChangeSettingsActivity::class.java)
            startActivity(intent)
        }

        btn_screensaver.setOnClickListener {
            val intent = Intent(this, CustomScreenActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        disposable = service.getSettings(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {result -> fillSettings(result)},
                {error -> Log.e(TAG, error.message)}
        )

        btn_logout.setOnClickListener {
            logout()

        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            currentUser = auth.currentUser as FirebaseUser
        }
        else
            startLoginActivity()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
        startLoginActivity()
    }

    private fun fillSettings(settings : Settings){
        this.settings = settings
        var wakeSleep = settings.Wake_SleepTime.split("/")
        var wake = ""
        var sleep = ""
        wakeSleep.forEach { time ->
            if(time.contains('w')){
                wake += time.substring(time.indexOf(';'))
            }
            if(time.contains('s')){
                sleep += time.substring(time.indexOf(';'))
            }
        }
        var s = "Brightness: ${settings.Brightness}"
        wake.split(';').forEach { wakeTime ->
            if(wakeTime.length > 1)
                s += "\nWake time: ${wakeTime.substring(wakeTime.indexOf(';') + 1)}"
        }
        sleep.split(';').forEach{ sleepTime ->
            if(sleepTime.length > 1)
                s += "\nSleep time: ${sleepTime.substring(sleepTime.indexOf(';') + 1)}"
        }
        s += "\nLocation: ${settings.Location}\nUnit: ${settings.Unit}"
        lbl_currentSettings.setText(s)
    }

    private fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
