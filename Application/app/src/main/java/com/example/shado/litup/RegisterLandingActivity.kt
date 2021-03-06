package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shado.litup.Model.Settings
import com.example.shado.litup.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register_landing.*

class RegisterLandingActivity : AppCompatActivity() {
    private val TAG : String = "RegisterLandingActivity"

    private val service = RetrofitInstance.getRetrofitInstance().create(LitUpDataService::class.java)

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    private var firebaseToken : String? = null
    private lateinit var currentUserInfo : User

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_landing)

        auth = FirebaseAuth.getInstance()

        LayoutFix()

        /*btn_setup.setOnClickListener {
            val intent = Intent(this, SetupDeviceLandingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }*/
    }

    private fun LayoutFix(){
        val prev = intent.getStringExtra("prev")
        var intent: Intent

        if (prev == "device"){
            txt_ltitle.text = "Pairing complete!"
            txt_ldetails.text = "The LitUp device is now linked\n" +
                    "with your smartphone. Next step\n" +
                    "is to connect it to your home wifi"
            btn_setup.text = "Continue"
            btn_setup.setOnClickListener {
                intent = Intent(this, SetupDeviceWifiActivity::class.java)
                StartNextActivity(intent)
            }
        }
        if (prev == "wifi"){
            txt_ltitle.text = "Home network connected!"
            txt_ldetails.text = "The LitUp device is now\n" +
                    "connected to your home network.\n" +
                    "You can change the settings\n" +
                    "in the device settings."
            btn_setup.text = "Continue"
            btn_setup.setOnClickListener {
                saveToAPI()
                intent = Intent(this, HomeActivity::class.java)
                StartNextActivity(intent)
            }
        }
        if (prev != "device" && prev != "wifi"){
            btn_setup.setOnClickListener {
                val intent = Intent(this, SetupDeviceLandingActivity::class.java)
                StartNextActivity(intent)
            }
        }
    }

    private fun StartNextActivity(intent: Intent){
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        btn_setup.setOnClickListener{null}
        //finish()
    }

    fun saveToAPI(){
        currentUser = auth.currentUser as FirebaseUser
        var token = currentUser.getIdToken(true)
        var settings = Settings()
        settings.DeviceName = "First device"
        settings.Location = "Antwerp"
        settings.Brightness = 50
        settings.WakeTime = "8:00"
        settings.SleepTime = "22:00"
        settings.Unit = "C"
        token.addOnCompleteListener { result ->
            firebaseToken = result.result?.token
            Log.d(TAG, firebaseToken)
            service.getUser("Bearer " + firebaseToken, currentUser.uid).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    { result ->
                        currentUserInfo = result
                        if (currentUserInfo.PersonalSettings.Id != 0)
                            disposable = service.updateSettings(currentUserInfo.PersonalSettings.Id.toInt(), settings,"Bearer " + firebaseToken).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                    { result ->
                                        Log.d(TAG, "Settings saved to API")
                                        intent = Intent(this, HomeActivity::class.java)
                                        StartNextActivity(intent)
                                    },
                                    { error -> Log.e(TAG, error.message) }
                            )
                    },
                    { error -> Log.e(TAG, error.message) })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
