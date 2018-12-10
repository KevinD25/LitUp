package com.example.shado.litup

import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_device_change_settings.*
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
                activity, // Context
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, // Theme
                this, // TimePickerDialog.OnTimeSetListener
                hour, // Hour of day
                minute,
                false // Is 24 hour view
        )
    }


    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val tv:TextView = activity.findViewById<TextView>(R.id.txt_sleep) as TextView
        tv.text = "$hourOfDay:$minute"

    }


    override fun onCancel(dialog: DialogInterface?) {
        Toast.makeText(activity,"Picker Canceled.",Toast.LENGTH_SHORT).show()
        super.onCancel(dialog)
    }

    private fun getAMPM(hour:Int):String{
        return if(hour>11)"PM" else "AM"
    }


    // Custom method to get hour for AM PM time format
    private fun getHourAMPM(hour:Int):Int{
        // Return the hour value for AM PM time format
        var modifiedHour = if (hour>11)hour-12 else hour
        if (modifiedHour == 0){modifiedHour = 12}
        return modifiedHour
    }
}