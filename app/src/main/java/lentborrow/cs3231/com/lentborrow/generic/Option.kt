package lentborrow.cs3231.com.lentborrow.generic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_option.*
import lentborrow.cs3231.com.lentborrow.R
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.auth.LoginController
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController

class Option : AppCompatActivity() {


    //global variables
    //UI elements


    //Firebase references

    val lCon = LoginController()
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        val isLogin = lCon.isLogedin()
        val lvCon = LocalValueController(this)
        val email = lvCon.getEmail()
        val password = lvCon.getPassword()
        if (isLogin) {
            lCon.Login(this, email, password, { email, password ->
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {

                } else {
                    getUserFailed()
                }
            }, { task ->

            })
        } else {
            getUserFailed()
        }

//        val user = FirebaseAuth.getInstance().currentUser
//        val txtNewPass = passwording.text.toString()
//        val txtNewEmail = emailing.text.toString()
//
//        user!!.updatePassword(txtNewPass).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                println("Update Success")
//            } else {
//                println("Error Update")
//            }
//        }
//        user!!.updateEmail(txtNewEmail).addOnCompleteListener {task->
//            if(task.isSuccessful) {
//                println("Update Success")
//            }else{
//                println("Error Update")
//            }
//
//        }

//        mAuth = FirebaseAuth.getInstance()
//
//        btnResetPassword.setOnClickListener {
//            val database = FirebaseDatabase.getInstance();
//            val myRef = database.getReference("password");
//            val c = passwording.text.toString()
//            myRef.setValue(c)
//        }

    }

    fun email(v: View) {
        val user = FirebaseAuth.getInstance().currentUser
        val txtNewEmail = emailing.text.toString()
        if (user != null) {
            user!!.updateEmail(txtNewEmail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(txtNewEmail, "Update Success")
                    val uCon = UserController()
                    val lCon = LocalValueController(this)
                    val email = lCon.getEmail()
                    uCon.getUserByEmail(email,{user ->
                        user.email = txtNewEmail
                        uCon.update(user)
                        logout()
                    },{error ->
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG)
                        logout()
                    })

                } else {
                    Log.d(txtNewEmail, "Error Update")
                    logout()

                }
            }
        }

    }

    fun password(v: View) {
        val user = FirebaseAuth.getInstance().currentUser
        val txtNewPass = passwording.text.toString()
        if (user != null) {
            user!!.updatePassword(txtNewPass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Update Success")
                    logout()
                } else {
                    println("Error Update")


                }
            }
        }
    }
    fun username(v:View){
        val username = usernaming.text.toString()

        if(!username.isEmpty()){
            val uCon = UserController()
            val lCon = LocalValueController(this)
            val id = lCon.getID()
            uCon.getUserByEmail(lCon.getEmail(),{user ->
                user.userName = username
                uCon.update(user)
            },{error ->
Toast.makeText(this,"Error",Toast.LENGTH_LONG)
            })

        }
    }
    fun getUserFailed() {
        val mCon = MessageController(this)
        mCon.showToast("Get user failed. Logout from app.")
        logout()
    }
    fun logout(){
        lCon.logOut()
        val amCon = ActivityMigrationController()
        amCon.setLoginActivity(this).go();
        finish()
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



