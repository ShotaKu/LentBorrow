package lentborrow.cs3231.com.lentborrow.generic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_option.*
import kotlinx.android.synthetic.main.activity_request_box.*
import lentborrow.cs3231.com.lentborrow.R
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.auth.LoginController
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController

class Option : AppCompatActivity() {


    val lCon = LoginController()
    var userData:FirebaseUser? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        val mCon = MessageController(this)

        val isLogin = lCon.isLogedin()
        val lvCon = LocalValueController(this)
        val email = lvCon.getEmail()
        val password = lvCon.getPassword()
        if (isLogin) {
            lCon.Login(this,email,password,{email, password ->
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    userData = user;
                } else {
                    getUserFailed()
                }
            },{ task ->

            })
        } else {
            getUserFailed()
        }

    }

    fun onClickChangePassword(view:View){
        val newPass = passwording.text.toString()

        if(!newPass.isEmpty()){
            userData!!.updatePassword(newPass).addOnCompleteListener(){task ->
                if(task.isSuccessful){
                    logout()
                }else{
                    val mCon = MessageController(this)
                    mCon.showToast("Fail to update password")
                }
            }
        }
    }

    fun onClickChangeUserName(view:View){
        val newUserName = userNaming.text.toString()
        val mCon = MessageController(this)
        if(!newUserName.isEmpty()){
            val uCon = UserController()
            val lvCon = LocalValueController(this);
            uCon.getUserByID(lvCon.getID(),{user ->
                user.userName = newUserName
                uCon.update(user);
                mCon.showToast("User name updated!")
            },{error ->
                mCon.showToast("Fail to get user data. Please login again.")
                logout()
            })
            //uCon
        }
    }

    fun onClickChangeEmail(view: View){
        val newEmail = emailing.text.toString()
        if(!newEmail.isEmpty()){
            userData!!.updateEmail(newEmail).addOnCompleteListener(){task ->
                if(task.isSuccessful){
                    logout()
                }else{
                    val mCon = MessageController(this)
                    mCon.showToast("Fail to update email")
                }
            }
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
