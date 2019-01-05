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
    private lateinit var currentUserInfo : User

    private val service = RetrofitInstance.getRetrofitInstance().create(LitUpDataService::class.java)

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
            if(currentUserInfo != null)
                if(currentUserInfo.PersonalSettings != null)
                    intent.putExtra("settingsId", currentUserInfo.PersonalSettings.Id)
            else intent.putExtra("settingsId", 0)
            startActivity(intent)
        }

        btn_screensaver.setOnClickListener {
            val intent = Intent(this, CustomScreenActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        btn_logout.setOnClickListener {
            logout()

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

    fun logout(){
        FirebaseAuth.getInstance().signOut()
        startLoginActivity()
    }

    private fun startLoginActivity(){
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    fun setUser(user : User){
        currentUserInfo = user
        Log.d(TAG, user.Id.toString())
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
