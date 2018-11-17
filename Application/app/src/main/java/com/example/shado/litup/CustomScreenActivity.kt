package com.example.shado.litup

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.support.v4.content.res.ResourcesCompat;
import android.view.ViewGroup
import android.widget.TableRow
//import com.example.shado.litup.R.id.tableRow
import kotlinx.android.synthetic.main.activity_custom_screen.*
import android.widget.LinearLayout
import android.R.attr.y
import android.R.attr.x
import android.graphics.Point
import android.view.Display
import android.R.attr.button
import android.util.Log
import junit.framework.Test
import java.util.logging.Logger
import android.os.Build
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.ViewTreeObserver
import android.widget.TextView






class CustomScreenActivity : AppCompatActivity() {

    val ROWS = 10
    val COLUMNS = 5
    //val tableLayout by lazy { TableLayout(this) }
    var width : Int = 0
    var height : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_screen)

        generateRows(ROWS, COLUMNS)

    }

    private fun generateRows(rows: Int, cols: Int) {
        //val LL_Outer = findViewById(R.id.buttonZone) as LinearLayout
        var tableLayout = findViewById(R.id.buttonZone) as TableLayout

        for (i in 0 until rows) {

            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)

            for (j in 0 until cols) {

                val button = Button(this)
                button.apply {
                    layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT)

                    /*TODO add implementation*/
                }
                //button.layoutParams = LinearLayout.LayoutParams(width / 16 , ((height / 10) * 6)/16)
                row.addView(button)
                getScreenSize()
                Logger.getLogger(Test::class.java.name).warning(" width " + width.toString() + " | height " + height.toString())


            }
            tableLayout.addView(row)
        }
        //LL_Outer.addView(tableLayout)


    }

    private fun getScreenSize() {
        val LL_Outer = findViewById(R.id.buttonZone) as LinearLayout
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y


        //viewHeight = LL_Outer.height
    }


    private fun changeColor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*private fun generateButton() {
        val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout
        val button = Button(this)
        button.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        button.text = "Click me"
        button.setOnClickListener({
            button.text = "You just clicked me"
        })
        button.setBackgroundColor(Color.GREEN)
        button.setTextColor(Color.RED)
        constraintLayout.addView(button);
    }*/
}
