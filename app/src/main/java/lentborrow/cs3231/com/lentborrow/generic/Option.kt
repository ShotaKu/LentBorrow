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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_option.*
import lentborrow.cs3231.com.lentborrow.LoginActivity
import lentborrow.cs3231.com.lentborrow.R

class Option : AppCompatActivity() {


    //global variables
    //UI elements


    //Firebase references


    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)


        val user = FirebaseAuth.getInstance().currentUser


        btnResetPassword.setOnClickListener {
            val txtNewPass = passwording.text.toString()

            user!!.updatePassword(txtNewPass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Update Success")
                } else {
                    println("Error Update")
                }
            }
            btnEmail.setOnClickListener {
                val txtNewEmail = emailing.text.toString()
                user!!.updateEmail(txtNewEmail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        println("Update Success")
                    } else {
                        println("Error Update")
                    }
                    btnUser.setOnClickListener {
                        val c = username.text.toString()
                        val database = FirebaseDatabase.getInstance();
                        val myRef = database.getReference("User");

                        myRef.child("userName").setValue(c)

                    }


//        mAuth = FirebaseAuth.getInstance()
//
//        btnResetPassword.setOnClickListener {
//            val database = FirebaseDatabase.getInstance();
//            val myRef = database.getReference("password");
//            val c = passwording.text.toString()
//            myRef.setValue(c)
//        }

                }
            }
        }
    }
}

//            val email = email_login.text.toString()
//
//            if (TextUtils.isEmpty(email)) {
//                Toast.makeText(applicationContext, "Enter your email!", Toast.LENGTH_SHORT).show()
//            } else {
//                mAuth!!.sendPasswordResetEmail(email)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                Toast.makeText(this, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(this, "Fail to send reset password email!", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//            }
//        }
//
//        btnBack.setOnClickListener {
//            finish()
//        }
//    }
//}
//



