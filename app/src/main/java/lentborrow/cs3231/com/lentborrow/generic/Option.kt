package lentborrow.cs3231.com.lentborrow.generic

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider.getCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import lentborrow.cs3231.com.lentborrow.LoginActivity
import lentborrow.cs3231.com.lentborrow.R

class Option : AppCompatActivity() {


    //global variables
    //UI elements


    //Firebase references


    //UI elements
    private var etEmail: EditText? = null
    private var btnSubmit: Button? = null
    //Firebase references
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

         var mAuth: FirebaseAuth? = null


        initialise()
    }

    private fun initialise() {
        var etEmail = findViewById<View>(R.id.email_login) as EditText
        btnSubmit = findViewById<View>(R.id.Send) as Button
        var mAuth = FirebaseAuth.getInstance()
        btnSubmit!!.setOnClickListener { sendPasswordResetEmail() }
    }


    private fun sendPasswordResetEmail() {
        val email = etEmail?.text.toString()
        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val message = "Email sent."
                            Log.d("message", message)
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            updateUI()
                        } else {
                            Log.w("message", task.exception!!.message)
                            Toast.makeText(this, "No user found with this email.", Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}






