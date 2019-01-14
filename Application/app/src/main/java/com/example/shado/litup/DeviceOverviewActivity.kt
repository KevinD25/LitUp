package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_device_overview.*

class DeviceOverviewActivity : AppCompatActivity() {

    var Hours = arrayListOf<Int>()
    var Minutes = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_overview)

        btnScreensaver.setOnClickListener{
            goToScreensaver()
        }

        fillSpinners()
    }

    private fun goToScreensaver(){
        val intent = Intent(this, CustomScreenActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun fillSpinners(){
        for (i in 0..23) {
            Hours.add(i)
        }
        for(i in 0..59){
            Minutes.add(i)
        }

        spnHourFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Hours)
        spnHourTill.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Hours)
        spnMinuteFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Minutes)
        spnMinuteTill.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Minutes)

    }
}
