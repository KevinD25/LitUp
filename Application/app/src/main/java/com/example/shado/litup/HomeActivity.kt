package com.example.shado.litup

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    var breedte: Int = 0
    var hoogte: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        addDevice.setOnClickListener{

        }
        device.setOnClickListener{

        }
    }

    private fun generateButtons(view: View){
        getScreenSize()
        var recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.md_recyclerview_content) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)



        val button_dynamic = ImageButton(this)
        // setting layout_width and layout_height using layout parameters
        button_dynamic.layoutParams = LinearLayout.LayoutParams(breedte/4, breedte + (breedte/2) )
        // add Button to LinearLayout
        recyclerView.addView(button_dynamic)



        //recyclerView.adapter = EnrollmentsAdapter()
    }

    private fun getScreenSize(){
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        breedte = size.x
        hoogte = size.y
    }
}
