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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var TAG : String = "LoginActivity: "
    private val RC_SIGN_IN = 9001

    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        // Configure Google Sign In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_clientId))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        btn_google_login.setOnClickListener{
            googleSignIn()
        }

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

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        startMainactivity()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    }

                    // ...
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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }
}
