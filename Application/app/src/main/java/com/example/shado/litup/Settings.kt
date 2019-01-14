package com.example.shado.litup

/**
 * Created by Jorren on 3/12/2018.
 */
class Settings {
    private lateinit var id : Number
    private lateinit var deviceName: String
    private lateinit var brightness: Number
    private lateinit var wake_SleepTime: String
    private lateinit var wakeTime: String
    private lateinit var sleepTime: String
    private lateinit var location: String
    private lateinit var unit: String

    var Id : Number
        get() {return id}
        set(value) {id = value}

    var DeviceName : String
        get(){return deviceName}
        set(value) {deviceName = value}

    var Brightness: Number
        get() {
            return brightness
        }
        set(value) {
            brightness = value
        }

    var Wake_SleepTime: String
        get() {
            return wake_SleepTime
        }
        set(value) {
            wake_SleepTime = value
            var wakeSleepList = wake_SleepTime.split("/")
            wakeTime = ""
            sleepTime = ""
            wakeSleepList.forEach { time ->
                if (time.contains('w')) {
                    wakeTime = time.substring(time.indexOf(';')+1)
                }
                if (time.contains('s')) {
                    sleepTime = time.substring(time.indexOf(';')+1)
                }
            }
            /*wake.split(';').forEach { wakeTime ->
                if(wakeTime.length > 1)
                    s += "\nWake time: ${wakeTime.substring(wakeTime.indexOf(';') + 1)}"
            }
            sleep.split(';').forEach{ sleepTime ->
                if(sleepTime.length > 1)
                    s += "\nSleep time: ${sleepTime.substring(sleepTime.indexOf(';') + 1)}"
            }*/
        }

    var WakeTime: String
        get() {
            //setWakeSleepTime()
            return wakeTime
        }
        set(value) {
            wakeTime = value
        }

    var SleepTime: String
        get() {
            //setWakeSleepTime()
            return sleepTime
        }
        set(value) {
            sleepTime = value
        }

    var Location: String
        get() {
            return location
        }
        set(value) {
            location = value
        }
    var Unit: String
        get() {
            return unit
        }
        set(value) {
            unit = value
        }

    fun setWakeSleepTime(){
        var wakeSleepList = wake_SleepTime.split("/")
        wakeTime = ""
        sleepTime = ""
        wakeSleepList.forEach { time ->
            if (time.contains('w')) {
                wakeTime = time.substring(time.indexOf(';')+1)
            }
            if (time.contains('s')) {
                sleepTime = time.substring(time.indexOf(';')+1)
            }
        }
    }
}