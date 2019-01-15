package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_settings.*
import java.util.regex.Pattern

class UserSettingsActivity : AppCompatActivity() {

    val user = FirebaseAuth.getInstance().currentUser
    var name:String? = ""
    var email:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        user?.let {
            name = user.displayName
            email = user.email
        }

        txt_username.text = name
        txt_email.text = email

        backbutton.setOnClickListener{
            super.onBackPressed()
        }
        btnLogout.setOnClickListener{
            logout()
        }
        btn_save.setOnClickListener {
            CheckChange()
        }
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        startLoginActivity()
    }

    private fun CheckChange(){
        if (!etxt_oldpass.text.toString().equals("") && !etxt_newpass.text.toString().equals("") && !etxt_renewpass.text.toString().equals("")) {
            val credential = EmailAuthProvider.getCredential(email.toString(), etxt_oldpass.text.toString())

            user?.reauthenticate(credential)
                    ?.addOnCompleteListener {
                        if (etxt_newpass.text.toString().equals(etxt_renewpass.text.toString())){
                            //SendChanges()
                            if (!checkPassValid(etxt_newpass.text.toString())){
                                etxt_newpass.setError("Password must include at least one upper case, one lowercase,  one number and one special character")
                            }
                            else{
                                SendChanges()
                            }
                        }
                        else{
                            etxt_newpass.setError("Passwords do not match")
                            etxt_renewpass.setError("Passwords do not match")
                        }
                    }
                    ?.addOnFailureListener {
                        etxt_oldpass.setError("Password is incorrect!")
                    }
        }
        else{
            if(etxt_oldpass.text.toString().equals(""))
                etxt_oldpass.setError("Old password is blank!")
            if(etxt_newpass.text.toString().equals(""))
                etxt_newpass.setError("New password is blank!")
            if(etxt_renewpass.text.toString().equals(""))
                etxt_renewpass.setError("Repeat password is blank!")
        }
    }

    private fun SendChanges(){
        user!!.updatePassword(etxt_newpass.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Save complete!", Toast.LENGTH_SHORT).show()
                startLoginActivity()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun startLoginActivity(){
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun checkPassValid(pass : String) : Boolean{
        if(pass.length >= 8){
            var PATTERN_ = "(?=.*[0-9]+)(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[@#\\$%^&+=!]+).*"
            var pattern = Pattern.compile(PATTERN_)
            return pattern.matcher(pass).matches()
        }else return false
    }

}
