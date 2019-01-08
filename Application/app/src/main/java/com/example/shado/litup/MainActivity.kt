package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shado.litup.Model.User
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
    private var currentUserInfo : User? = null  // changed due to error

    private val service = RetrofitInstance.getRetrofitInstance().create(LitUpDataService::class.java)

    var disposable :Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.)
        setContentView(R.layout.activity_main)

        btn_devicesetup.setOnClickListener{
            val intent = Intent(this, DeviceSetupActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        btn_changesettings.setOnClickListener {
            val intent = Intent(this, ChangeSettingsActivity::class.java)
            if(currentUserInfo != null)
                if(currentUserInfo!!.PersonalSettings != null)
                    intent.putExtra("settingsId", currentUserInfo!!.PersonalSettings.Id)
            else intent.putExtra("settingsId", 0)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        btn_screensaver.setOnClickListener {
            val intent = Intent(this, CustomScreenActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        auth = FirebaseAuth.getInstance()

        btn_logout.setOnClickListener {
            logout()

        }

        btnUserSettings.setOnClickListener{
            userSettings()
        }

        btnHelp.setOnClickListener{
            FAQ()
        }

        btn_setup.setOnClickListener {
            val intent = Intent(this, SetupDeviceLandingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            currentUser = auth.currentUser as FirebaseUser
            var token = currentUser.getIdToken(true)
            token.addOnCompleteListener {
                result ->
                    var firebaseToken = result.result?.token
                    Log.d(TAG, firebaseToken)
                    service.getUser("Bearer " + firebaseToken, currentUser.uid).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            {result -> setUser(result)},
                            {error -> Log.e(TAG, error.message)})
            }
        }
        else
            startLoginActivity()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        startLoginActivity()
    }

    private fun userSettings(){
        val intent = Intent(this, UserSettingsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun FAQ(){
        val intent = Intent(this, FAQActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun startLoginActivity(){
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun setUser(user : User){
        currentUserInfo = user
        Log.d(TAG, user.Id.toString())
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
