package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_login.setOnClickListener {
            startActivity("Login")
        }
        btn_register.setOnClickListener {
            startActivity("Register")
        }
    }

    private fun startActivity(Activity:String){
        val intent: Intent;

        when (Activity){
            "Login" -> {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            "Register" -> {
                intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            else -> {
                print("Error starting activity")
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
