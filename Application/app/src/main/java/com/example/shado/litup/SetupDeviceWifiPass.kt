package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setup_device_wifi_pass.*

class SetupDeviceWifiPass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_device_wifi_pass)

        btn_next.setOnClickListener {
            StartNextActivity()
        }
    }

    private fun StartNextActivity(){
        val intent = Intent(this, RegisterLandingActivity::class.java)
        intent.putExtra("prev", "wifi")
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
