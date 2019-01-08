package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

open class RegisterActivity : AppCompatActivity() {
    private var TAG : String = "LoginActivity: "

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            if(checkEqualPass(etxt_pass.text.toString(),etxt_repass.text.toString())){
                if (!etxt_email.text.toString().equals("") && !etxt_pass.text.toString().equals("") && !etxt_repass.text.toString().equals("")) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(etxt_email.text.toString()).matches())
                        createUser(etxt_email.text.toString(), etxt_pass.text.toString())
                    else
                        etxt_email.setError("Please use a valid email address")
                }
                else{
                    if(etxt_email.text.toString().equals(""))
                        etxt_email.setError("email is blank!")
                    if(etxt_pass.text.toString().equals(""))
                        etxt_pass.setError("password is blank!")
                    if(etxt_repass.text.toString().equals(""))
                        etxt_repass.setError("password is blank!")
                }
            }
            else
                etxt_repass.setError("Passwords do not match!")
        }

        etxt_pass.setOnFocusChangeListener { view, b ->
            if(!checkPassValid(etxt_pass.text.toString())) {
                etxt_pass.setError("Password must include at least one upper case, one lowercase,  one number and one special character")
                btn_register.isEnabled = false
            }else btn_register.isEnabled = true
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ create ->
                    if(create.isSuccessful){
                        Log.d(TAG, "User created")
                        startMainactivity()
                    }
                    else{
                        Log.e(TAG, "Creating user failed")
                        Toast.makeText(baseContext, "Something went wrong, \nPlease try again", Toast.LENGTH_LONG).show()
                    }
                }
    }

    fun checkEqualPass(pass1 : String, pass2 : String) : Boolean {
        if (pass1.equals(pass2))
            return true
        return false
    }

    fun checkPassValid(pass : String) : Boolean{
        if(pass.length >= 8){
            var PATTERN_ = "(?=.*[0-9]+)(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[@#\\$%^&+=!]+).*"
            var pattern = Pattern.compile(PATTERN_)
            return pattern.matcher(pass).matches()
        }else return false
    }

    private fun startMainactivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
