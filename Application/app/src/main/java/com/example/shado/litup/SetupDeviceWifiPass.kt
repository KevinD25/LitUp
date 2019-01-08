package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SetupDeviceWifiPass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_device_wifi_pass)

        StartNextActivity()
    }

    private fun StartNextActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
