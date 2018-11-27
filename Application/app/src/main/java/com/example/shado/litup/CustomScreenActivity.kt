package com.example.shado.litup

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Point
import android.net.sip.SipSession
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_custom_screen.*
import me.priyesh.chroma.ChromaDialog
import me.priyesh.chroma.ColorMode
import me.priyesh.chroma.ColorSelectListener


class CustomScreenActivity : AppCompatActivity() {


    var breedte : Int = 0
    var hoogte : Int = 0
    private var mColor: Int = -65536


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_screen)

        var buttonzone = findViewById(R.id.buttonZone) as LinearLayout
        buttonzone.addView(getTableWithAllRowsStretchedView())

        updateColorText(mColor)
        updateColorButton(mColor)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun getTableWithAllRowsStretchedView() : LinearLayout {

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
                button.layoutParams = TableRow.LayoutParams(breedte/16, ((hoogte/10)*7)/14, 1.0f)

                button.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View, m: MotionEvent): Boolean {
                        // Perform tasks here
                        changeColor(button)
                       /* var location : IntArray = intArrayOf(0,0)

                        button.getLocationOnScreen(location);*/
                        return true
                    }
                })

                    tableRow.addView(button)
                }
            tableLayout.addView(tableRow)
        }
        linearLayout.addView(tableLayout)
        return linearLayout
    }

    private fun getScreenSize() {
        val LL_Outer = findViewById(R.id.buttonZone) as LinearLayout
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        breedte = size.x
        hoogte = size.y


        //viewHeight = LL_Outer.hoogte
    }

    private fun changeColor(button:ImageButton) {

        button.setBackgroundColor(mColor)
    }

    private fun updateColorButton(color:Int){
        btnKleur.setBackgroundColor(color)
    }

    private fun updateColorText(color:Int){
        val hexColor = String.format("#%06X", 0xFFFFFF and color)
        txtColor.setText(hexColor)
    }

    fun onClickColorPicker(view:View){
         ChromaDialog.Builder()
                .initialColor(mColor)
                .colorMode(ColorMode.RGB) // There's also ARGB and HSV
                .onColorSelected(object : ColorSelectListener {
                    override fun onColorSelected(color: Int) {
                        updateColorButton(color)
                        updateColorText(color)
                        mColor = color
                    }
                })
        .create()
                .show(getSupportFragmentManager(), "ChromaDialog");
    }
}
