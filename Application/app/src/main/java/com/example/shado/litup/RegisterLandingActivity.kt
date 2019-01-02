package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_landing.*

class RegisterLandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_landing)

        btn_setup.setOnClickListener {
            //TODO: Add intent to next page
            /*val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
        }
    }
}
