package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            intent.putExtra("settingsId", 1)
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
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
