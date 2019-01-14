package com.example.shado.litup

import android.content.Intent
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_device_setup.*
import kotlinx.android.synthetic.main.activity_setup_device_wifi_pass.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class SetupDeviceWifiPass : AppCompatActivity() {

    var password : String = ""
    var wifi : String = ""
    lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_device_wifi_pass)

        btn_next.setOnClickListener {
            checkPassword()
        }

        wifi = intent.getStringExtra("wifi")
        txtWifiNetwork.text = wifi
    }

    private fun StartNextActivity(){
        val intent = Intent(this, RegisterLandingActivity::class.java)
        intent.putExtra("prev", "wifi")
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun checkPassword(){
        if(!etxtPassword.text.trim().toString().equals("")){
            password = etxtPassword.text.trim().toString()

            send()

            StartNextActivity()
        }
    }


    private fun send() {
        var ssid = wifi
        var passwd = password

        //TODO Dummy data
        var city = "Antwerp"
        var temp = "C"

        /*if (radiogroup.checkedRadioButtonId == 2131230855) {
            temp = "C"
        } else if (radiogroup.checkedRadioButtonId == -1) {
            temp = ""
        } else {
            temp = "F"
        }*/
        var timezone = "Brussels"

        var param = "ssid=" + ssid + "&passwd=" + passwd + "&city=" + city + "&temp=" + temp + "&timezone=" + timezone

            doAsync {
                val result = URL("http://192.168.50.5?" + param).readText()
                uiThread {
                    Log.d("Request", result)
                }

            }

    }
}
