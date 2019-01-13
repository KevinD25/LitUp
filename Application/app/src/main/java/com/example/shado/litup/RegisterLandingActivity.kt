package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_landing.*

class RegisterLandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_landing)

        val prev = intent.getStringExtra("prev")
        var intent: Intent

        if (prev == "device"){
            txt_ltitle.text = "Paring complete!"
            txt_ldetails.text = "The LitUp device is now linked" +
                    "with your smartphone. Next step" +
                    "is to connect it to your home wifi"
            btn_setup.text = "Continue"
            btn_setup.setOnClickListener {
                intent = Intent(this, SetupDeviceWifiActivity::class.java)
                StartNextActivity(intent)
            }
        }
        if (prev == "wifi"){
            txt_ltitle.text = "Home network connected!"
            txt_ldetails.text = "The LitUp device is now" +
                    "connected to your home network." +
                    "Just a few more step and the" +
                    "setup is complete."
            btn_setup.text = "Continue"
            btn_setup.setOnClickListener {
                intent = Intent(this, MainActivity::class.java)
                StartNextActivity(intent)
            }
        }
        else {
            btn_setup.setOnClickListener {
                val intent = Intent(this, SetupDeviceLandingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }


        /*btn_setup.setOnClickListener {
            val intent = Intent(this, SetupDeviceLandingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }*/
    }

    private fun StartNextActivity(intent: Intent){
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
