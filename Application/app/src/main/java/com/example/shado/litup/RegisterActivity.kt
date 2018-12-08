package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private var TAG : String = "LoginActivity: "

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            if(checkEqualPass(txt_pass_register.text.toString(),txt_pass2_register.text.toString())){
                if (!txt_email_register.text.toString().equals("") && !txt_pass_register.text.toString().equals("") && !txt_pass2_register.text.toString().equals("")) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(txt_email_register.text.toString()).matches())
                        createUser(txt_email_register.text.toString(), txt_pass_register.text.toString())
                    else
                        txt_email_register.setError("Please use a valid email address")
                }
                else{
                    if(txt_email_register.text.toString().equals(""))
                        txt_email_register.setError("email is blank!")
                    if(txt_pass_register.text.toString().equals(""))
                        txt_pass_register.setError("password is blank!")
                    if(txt_pass2_register.text.toString().equals(""))
                        txt_pass2_register.setError("password is blank!")
                }
            }
            else
                txt_pass2_register.setError("Passwords do not match!")
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

    private fun startMainactivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
