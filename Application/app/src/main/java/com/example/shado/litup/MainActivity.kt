package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_devicesetup.setOnClickListener{
            val intent = Intent(this, DeviceSetupActivity::class.java)
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

    public fun logout(){
        FirebaseAuth.getInstance().signOut()
        startLoginActivity()
    }

    private fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
