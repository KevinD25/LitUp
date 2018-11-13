package com.example.shado.litup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            auth.signInWithEmailAndPassword(txt_email.text.toString(), txt_pass.text.toString())
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
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            val currentUser = auth.currentUser
            startMainactivity()
        }
    }

    private fun startMainactivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
