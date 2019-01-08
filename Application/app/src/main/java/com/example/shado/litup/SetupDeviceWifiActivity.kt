package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setup_device_wifi.*

class SetupDeviceWifiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_device_wifi)

        btn_back.setOnClickListener {
            StartNextActivity()
        }
    }

    private fun StartNextActivity(){
        val intent = Intent(this, SetupDeviceWifiPass::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
