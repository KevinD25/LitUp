package com.example.shado.litup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_device_change_settings.*
import kotlinx.android.synthetic.main.activity_device_setup.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class ChangeSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_change_settings)

        seekbar.setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                lbl_brightnessvalue.text = "$i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        txt_sleep.setOnClickListener{
            val newFragment = TimePickerFragment()
            newFragment.show(fragmentManager, "Time Picker")
        }
        txt_wake.setOnClickListener{
            val newFragment = TimePickerFragment()
            newFragment.show(fragmentManager, "Time Picker")
        }

        btn_save.setOnClickListener{
            var city = txt_changecity.text
            var brightness = lbl_brightness.text
            var sleepTime = txt_sleep.text
            var wakeTime = txt_wake.text

            var param = "sleep=" + sleepTime + "&wake=" + wakeTime + "&city=" + city + "&brightness=" + brightness
            if(emptycheck(city.toString(), brightness.toString(), sleepTime.toString(), wakeTime.toString())) {
                doAsync {
                    val result = URL("http://raspberrypi.local?" + param).readText()
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

    fun emptycheck(city: String, brightness: String, sleep: String, wake: String): Boolean{
        return(city != "" && brightness != "" && sleep != "" && wake != "")
    }
}
