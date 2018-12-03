package com.example.shado.litup

/**
 * Created by Jorren on 3/12/2018.
 */
class Settings {
    private lateinit var brightness : Number
    private lateinit var wake_sleepTime : List<Number>
    private lateinit var location : String
    private lateinit var unit : String

    var Brightness : Number
        get() {return brightness}
        set(value) {brightness = value}

    var Wake_SleepTime : List<Number>
        get() {return wake_sleepTime}
        set(value) {wake_sleepTime = value}
    var Location : String
        get() {return location}
        set(value) {location = value}
    var Unit : String
        get() {return unit}
        set(value) {unit = value}
}