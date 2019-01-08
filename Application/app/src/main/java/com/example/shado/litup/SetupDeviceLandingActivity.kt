package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_setup_device_landing.*

class SetupDeviceLandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_device_landing)

        btn_continue.setOnClickListener {
            val intent = Intent(this, SetupDeviceActivity::class.java)
            startActivity(intent)
        }
    }
}
