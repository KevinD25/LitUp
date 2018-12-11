package com.example.shado.litup

/**
 * Created by Jorren on 3/12/2018.
 */
class Settings {
    private lateinit var brightness: Number
    private lateinit var wake_SleepTime: String
    private lateinit var wake_Time: String
    private lateinit var sleep_Time: String
    private lateinit var location: String
    private lateinit var unit: String

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
            wake_Time = ""
            sleep_Time = ""
            wakeSleepList.forEach { time ->
                if (time.contains('w')) {
                    wake_Time = time.substring(time.indexOf(';')+1)
                }
                if (time.contains('s')) {
                    sleep_Time = time.substring(time.indexOf(';')+1)
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

    var Wake_Time: String
        get() {
            setWakeSleepTime()
            return wake_Time
        }
        set(value) {
            wake_Time = value
        }

    var Sleep_Time: String
        get() {
            setWakeSleepTime()
            return sleep_Time
        }
        set(value) {
            sleep_Time = value
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
        wake_Time = ""
        sleep_Time = ""
        wakeSleepList.forEach { time ->
            if (time.contains('w')) {
                wake_Time = time.substring(time.indexOf(';')+1)
            }
            if (time.contains('s')) {
                sleep_Time = time.substring(time.indexOf(';')+1)
            }
        }
    }
}