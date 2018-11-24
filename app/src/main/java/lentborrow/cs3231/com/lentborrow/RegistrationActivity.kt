package lentborrow.cs3231.com.lentborrow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_registration.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.auth.LoginController
import lentborrow.cs3231.com.lentborrow.controller.database.user.User
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class RegistrationActivity : AppCompatActivity() {
    //private var mAuth: FirebaseAuth? = null
    val lCon = LoginController();
    var mCon = MessageController(this);
    val uCon = UserController();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mCon = MessageController(this);
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (lCon.isLogedin()) {

        }
    }

    fun Register(view: View) {
        val email = email_registration.text.toString();
        val pass = passwordRegistration.text.toString();
        lCon.Register(this, email, pass,
                fun(email, pass) {
                    //Login after registration
                    val uName = userNameRegistration.text.toString();
                    val user = uCon.create(User(uName, email));
                    lCon.Login(this, email, pass,
                            fun(email: String, pass: String) {
                                val lvCon = LocalValueController(this);
                                lvCon.setEmail(email);
                                lvCon.setPassword(pass);
                                lvCon.setID(user.userID);
                                mCon.showToast("Hi," + email);
                                //@TODO go to Request box activity
                            }
                            , fun(task: Task<AuthResult>) {
                        //Failed
                        mCon.showToast("Login failed:" + task.exception)
                    }
                    );
                },
                fun(task) {
                    //Fail to register
                    mCon.showToast("Registration failed:" + task.exception)
                }
        )
    }

    fun toLogin(view: View) {
        val email = userNameRegistration.text.toString();
        val pass = passwordRegistration.text.toString();
        val amController = ActivityMigrationController();
        amController.setLoginActivity(this)
                .pass("email", email)
                .pass("password", pass)
                .go();
    }

}
