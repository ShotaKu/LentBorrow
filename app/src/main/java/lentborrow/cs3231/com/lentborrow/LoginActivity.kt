package lentborrow.cs3231.com.lentborrow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.auth.LoginController
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class LoginActivity : AppCompatActivity() {
    var fbAuth = FirebaseAuth.getInstance()

    //Awake
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        logOut()
    }

    fun logOut(){
        //@TODO check here
        val lCon = LoginController();
        lCon.logOut();
        val lvCon = LocalValueController(this)
        lvCon.setID("")
        lvCon.setPassword("")
        lvCon.setEmail("");
    }

    //Start
    override fun onResume() {
        super.onResume()

        val lCon = LocalValueController(this);
        var email = lCon.getEmail();
        var pass = lCon.getPassword()

        if (email.isEmpty() || pass.isEmpty()) {
            var intEmail = intent.getStringExtra("email");
            if(intEmail!=null)
                email = intEmail!!;
            var intPass = intent.getStringExtra("password")
            if(intPass!=null)
                pass = intPass!!;
        }
        email_login.setText(email, TextView.BufferType.EDITABLE);
        password_login.setText(pass, TextView.BufferType.EDITABLE);
    }

    //Login button function
    fun Login(view: View) {
        val name = email_login.text.toString();
        val pass = password_login.text.toString();
        login(view, name, pass);
    }

    //go to registration activity
    fun toRegistration(view: View) {
        val mail = email_login.text.toString();
        val pass = password_login.text.toString();
        val amCon = ActivityMigrationController();
        amCon.setRegistrationActivity(this)
                .pass("email", mail)
                .pass("password", pass)
                .go()
    }

    //
    fun login(view: View, email: String, password: String) {
        showMessage("Authenticating...")

        val lCon = LoginController();
        lCon.logOut();
        lCon.Login(this, email, password, { email, password ->
            run {
                showMessage("Hi " + email);
                val lvCon = LocalValueController(this);
                lvCon.setEmail(email);
                lvCon.setPassword(password);
                val uCon = UserController()
                uCon.getUserByEmail(email,{ user -> run{
                    //@TODO go to Request box activity
                    lvCon.setID(user.userID)
                    val amCon = ActivityMigrationController();
//                    amCon.setRequestBoxActivity(this)
//                            .pass("email",email)
//                            .go()
                }},{error ->
                    MessageController(this).showToast(error.message);
                })

            }
        }, { task ->
            showMessage("Error: ${task.exception?.message}")
        });
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
