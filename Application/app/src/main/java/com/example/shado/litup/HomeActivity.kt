package com.example.shado.litup

import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.shado.litup.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private val TAG : String = "HomeActivity"

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    private var firebaseToken : String? = null
    private var currentUserInfo : User? = null  // changed due to error

    private val service = RetrofitInstance.getRetrofitInstance().create(LitUpDataService::class.java)

    var disposable : Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnUserSettings.setOnClickListener{
            userSettings()
        }

        addDevice.setOnClickListener{
            val intent = Intent(this, DeviceSetupActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        device.setOnClickListener{
            val intent = Intent(this, ChangeSettingsActivity::class.java) //TODO: DeviceOverviewActivity ipv changesettings
            if(currentUserInfo != null)
                if(currentUserInfo?.PersonalSettings != null)
                    intent.putExtra("settingsId", currentUserInfo!!.PersonalSettings.Id)
                else intent.putExtra("settingsId", 0)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        btnUserSettings.setOnClickListener{
            goToUserSettings()
        }
        btnHelp.setOnClickListener {
            goToFaq()
        }

        auth = FirebaseAuth.getInstance()
    }

    private fun startLoginActivity(){
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun userSettings(){
        val intent = Intent(this, UserSettingsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun setUser(user : User){
        currentUserInfo = user
        Log.d(TAG, user.Id.toString())
        if(currentUserInfo?.PersonalSettings != null){
            disposable = service.getSettings("Bearer " + firebaseToken, currentUserInfo?.PersonalSettings?.Id!!.toInt()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                    { result ->
                        if(result.DeviceName.equals("") || result.DeviceName != null)
                            Log.d("Devicename", result.DeviceName)
                    },
                    { error -> Log.e(TAG, error.message) }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            currentUser = auth.currentUser as FirebaseUser
            var token = currentUser.getIdToken(true)
            token.addOnCompleteListener {
                result ->
                if(result.isSuccessful){
                    firebaseToken = result.result?.token
                    Log.d(TAG, firebaseToken)
                    service.getUser("Bearer " + firebaseToken, currentUser.uid).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            {result -> setUser(result)},
                            {error -> Log.e(TAG, error.message)})
                }
                result.addOnFailureListener {
                    Toast.makeText(this, "failed getting user info", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else
            startLoginActivity()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun goToUserSettings(){
        val intent = Intent(this, UserSettingsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun goToFaq(){
        val intent = Intent(this, FAQActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
