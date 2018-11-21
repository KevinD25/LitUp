package com.example.shado.litup

import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import kotlinx.android.synthetic.main.activity_device_setup.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL



class DeviceSetupActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_setup)

        btn_send.setOnClickListener{
            var ssid = txt_ssid.text
            var passwd = txt_pass.text
            var city = txt_city.text
            var temp = ""
            if (radiogroup.checkedRadioButtonId == 2131230856) {
                temp = "C"
            } else if (radiogroup.checkedRadioButtonId == -1) {
                temp = ""
            } else {
                temp = "F"
            }

            var param = "ssid" + "=" + ssid + "&" + "passwd" + "=" + passwd + "&" + "city" + "=" + city + "&" + "temp" + "=" + temp
            if(emptycheck(ssid.toString(), passwd.toString(), city.toString(), temp.toString())) {
                doAsync {
                    val result = URL("http://192.168.0.247?" + param).readText()
                    uiThread {
                        Log.d("Request", result)
                        lbl_response.text = result
                    }

                }
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Please fill in all the fields!")
                builder.setPositiveButton("Ok"){dialog, which ->}
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    fun emptycheck(ssid: String, passwd: String, city: String, temp: String): Boolean{
        return(ssid != "" && passwd != "" && city != "" && temp != "")
    }
}
