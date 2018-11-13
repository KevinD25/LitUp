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
import com.example.shado.litup.R.id.tableRow
import kotlinx.android.synthetic.main.activity_custom_screen.*


class CustomScreenActivity : AppCompatActivity() {

    val ROWS = 10
    val COLUMNS = 5
    val tableLayout by lazy { TableLayout(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_screen)


        generateButtonRow()

        generateRows(ROWS, COLUMNS)

    }

    private fun generateRows(rows: Int, cols: Int) {
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
                row.addView(button)
            }
            tableLayout.addView(row)
        }
        linearLayout.addView(tableLayout)


    }

    private fun generateButtonRow() {
        val tableLayout:TableLayout = findViewById(R.id.tableLayout) as TableLayout
        var tableRow = TableRow(this)
        val buttonList : MutableList<Button> = arrayListOf()
        //val rowList : MutableList<MutableList<Button>> = arrayListOf()
        val matrixSize:Int = 16
        for (i in 1..matrixSize) {
            val button = Button(this)
            button.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
            ResourcesCompat.getDrawable(getResources(), R.drawable.buttonshape, null);
            button.setOnClickListener({
                changeColor()
            })
            buttonList.add(button)

        }

        for(item  in buttonList){
            tableRow = TableRow(this)
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
            tableRow.setLayoutParams(lp)
            tableRow.addView(item)

        }

        for(item in buttonList){
            tableLayout.addView(tableRow)
        }

    }

    private fun changeColor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun generateButton() {
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
    }
}
