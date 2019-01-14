package com.example.shado.litup.Model

class Screensaver {
    private lateinit var name: String
    private lateinit var colors: MutableList<String>


    var Name: String
        get() {

            return name
        }
        set(value) {
            name = value
        }


    var Colors: MutableList<String>
        get() {
            return colors
        }
        set(value) {
            colors = value
        }
}