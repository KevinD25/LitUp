package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.URLSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var TAG : String = "LoginActivity: "

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            if (!txt_email.text.toString().equals("") && !txt_pass.text.toString().equals("")) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(txt_email.text.toString()).matches())
                    login(txt_email.text.toString(), txt_pass.text.toString())
                else
                    txt_email.setError("Please use a valid email address")
            }
            else{
                if(txt_email.text.toString().equals(""))
                    txt_email.setError("email is blank!")
                if(txt_pass.text.toString().equals(""))
                    txt_pass.setError("password is blank!")
            }
        }
        setRegisterLink()
    }

    private fun setRegisterLink() {
        val ssb = SpannableStringBuilder()
        ssb.append(btn_to_register.text)
        ssb.setSpan(URLSpan("http://www.google.com"), 0, ssb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        btn_to_register.setText(ssb, TextView.BufferType.SPANNABLE)
        btn_to_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        startMainactivity()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun startMainactivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            val currentUser = auth.currentUser
            startMainactivity()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
