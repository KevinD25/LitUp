package com.example.shado.litup

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.sip.SipSession
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.format.Formatter
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_custom_screen.*
import kotlinx.android.synthetic.main.activity_device_setup.*
import me.priyesh.chroma.ChromaDialog
import me.priyesh.chroma.ColorMode
import me.priyesh.chroma.ColorSelectListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL


class CustomScreenActivity : AppCompatActivity() {


    var breedte: Int = 0
    var hoogte: Int = 0
    private var mColor: Int = -65536
    val buttons: MutableList<ImageButton> = mutableListOf()
    val colors: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_screen)

        //var buttonzone = findViewById(R.id.buttonZone) as LinearLayout
        buttonZone.addView(getTableWithAllRowsStretchedView())

        updateColorButton(mColor)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun getTableWithAllRowsStretchedView(): LinearLayout {

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        val tableLayout = TableLayout(this)
        tableLayout.isStretchAllColumns = true
        tableLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        tableLayout.weightSum = 16.0f


        getScreenSize()

        for (i in 0..15) {
            val tableRow = TableRow(this)
            tableRow.gravity = Gravity.CENTER
            tableRow.layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f)

            for (j in 0..15) {
                val button = ImageButton(this)
                button.layoutParams = TableRow.LayoutParams(breedte / 16, ((hoogte / 10) * 7) / 14, 1.0f)

                button.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View, m: MotionEvent): Boolean {
                        // Perform tasks here
                        changeColor(button)
                        return true
                    }
                })

                buttons.add(button)

                tableRow.addView(button)
            }
            tableLayout.addView(tableRow)
        }
        linearLayout.addView(tableLayout)
        return linearLayout
    }

    private fun getScreenSize() {
        //val LL_Outer = findViewById(R.id.buttonZone) as LinearLayout
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        breedte = size.x
        hoogte = size.y
    }

    private fun changeColor(button: ImageButton) {
        button.setBackgroundColor(mColor)
    }

    private fun updateColorButton(color: Int) {
        btnKleur.setBackgroundColor(color)
    }


    fun onClickColorPicker(view: View) {
        ChromaDialog.Builder()
                .initialColor(mColor)
                .colorMode(ColorMode.RGB) // There's also ARGB and HSV
                .onColorSelected(object : ColorSelectListener {
                    override fun onColorSelected(color: Int) {
                        updateColorButton(color)
                        mColor = color
                    }
                })
                .create()
                .show(getSupportFragmentManager(), "ChromaDialog");
    }

    fun onClickSave(view: View) {
        if (txtName.text.toString().trim() != "") {
            colors.clear()
            var kleurID: Int = 0
            var hexColor: String
            for (item in buttons) {
                var buttonBackground = item.background;
                if (buttonBackground is ColorDrawable) {
                    kleurID = (buttonBackground as ColorDrawable).color;
                    hexColor = String.format("#%06X", 0xFFFFFF and kleurID)
                } else {
                    hexColor = "#000000"
                }

                colors.add(hexColor)
                Log.e("KLEUR", hexColor)
            }
            send()
        } else {
            toast("Please enter a name...")
        }
    }

    fun send() {
        var colorstring: String
        colorstring = createString()

        var deviceIp = getIpAddress()

        var param = "&screensaver=" + colorstring
        doAsync {
            val result = URL("http://" + deviceIp + "/screensaver?" + param).readText()
            uiThread {
                Log.d("Request", result)
            }
        }
    }

    fun getIpAddress() : String {
        var wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var ip = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        Log.d("ip address", ip)
        var deviceIp = ip.substring(0, ip.lastIndexOf('.') + 1) + "100"
        Log.d("device Ip", deviceIp)
        return deviceIp
    }

    fun createScreensaver() {
        var screensaver: Screensaver
        //screensaver.name = txtName.text.toString().trim()
        //TODO Save screensaver in object (list of colors)
    }

    fun createString(): String {
        var output: String = ""
        output = txtName.text.toString().trim() + ";"
        for (item in colors) {
            output += hex2Rgb(item) + ";"
        }
        //output = output.substring(0, output.length -1)
        return output
    }

    fun hex2Rgb(colorStr: String): String {
        var kleuren: String

        kleuren = Integer.valueOf(colorStr.substring(1, 3), 16).toString() + "/"
        kleuren += Integer.valueOf(colorStr.substring(3, 5), 16).toString() + "/"
        kleuren += Integer.valueOf(colorStr.substring(5, 7), 16).toString()

        return kleuren
    }
}
