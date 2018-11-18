package com.example.shado.litup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.view.ViewGroup
import android.widget.TableRow
//import com.example.shado.litup.R.id.tableRow
import android.widget.LinearLayout
import android.graphics.Point
import junit.framework.Test
import java.util.logging.Logger
import android.view.Gravity
import android.view.View


class CustomScreenActivity : AppCompatActivity() {

    val ROWS = 10
    val COLUMNS = 5
    //val tableLayout by lazy { TableLayout(this) }
    var breedte : Int = 0
    var hoogte : Int = 0

    val amountOfButtons : Int = 16


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_screen)

        var buttonzone = findViewById(R.id.buttonZone) as LinearLayout
        buttonzone.addView(getTableWithAllRowsStretchedView())
    }

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
                val button = Button(this)
                /*val param = button.layoutParams as TableRow.LayoutParams
                param.setMargins(0,0,0,0)
                param.width = breedte/16
                param.height = ((hoogte/10)*7)/16*/
                button.layoutParams = TableRow.LayoutParams(breedte/16, ((hoogte/10)*7)/16)
                //button.layoutParams = param

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

    private fun changeColor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
