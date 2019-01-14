package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_login.setOnClickListener {
            startActivity("Login")
        }
        btn_register.setOnClickListener {
            startActivity("Register")
        }

        auth = FirebaseAuth.getInstance()
    }

    private fun startActivity(Activity:String){
        val intent: Intent;

        when (Activity){
            "Login" -> {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            "Register" -> {
                intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            else -> {
                print("Error starting activity")
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun startMainactivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            val currentUser = auth.currentUser
            startMainactivity()
        }
    }
}
