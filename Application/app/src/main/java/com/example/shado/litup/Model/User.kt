package com.example.shado.litup.Model

/**
 * Created by Jorren on 13/12/2018.
 */
class User {
    private lateinit var id : Number
    private lateinit var firebaseId : String
    private lateinit var personalSettings : Settings

    var Id : Number
        get() {return id}
        set(value) {id = value}
    var FirebaseId : String
        get() {return firebaseId}
        set(value) {firebaseId = value}
    var PersonalSettings : Settings
        get() {return personalSettings}
        set(value) {personalSettings = value}
}