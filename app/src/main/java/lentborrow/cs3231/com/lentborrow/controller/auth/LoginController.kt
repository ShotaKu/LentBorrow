package lentborrow.cs3231.com.lentborrow.controller.auth

import android.app.Activity
import android.app.FragmentHostCallback
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginController()
{
    var fbAuth:FirebaseAuth = FirebaseAuth.getInstance();

    init {
        fbAuth = FirebaseAuth.getInstance();
    }

    fun Login(activity:Activity, email: String, password:String
              , successCallback: (email: String, password:String) -> Unit   // Unit = void
              , failedCallback:(Task<AuthResult>) -> Unit){
        fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, OnCompleteListener<AuthResult>
        { task ->
            if(task.isSuccessful){
                successCallback(email,password);
            }else{
                failedCallback(task);
            }
        })
    }

    fun Register(activity:Activity, email: String, password:String
              , successCallback: (email: String, password:String) -> Unit   // Unit = void
              , failedCallback:(Task<AuthResult>) -> Unit){
        fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, OnCompleteListener<AuthResult>
        { task ->
            if(task.isSuccessful){
                successCallback(email,password);
            }else{
                failedCallback(task);
            }
        })
    }

    fun isLogedin():Boolean {
        return fbAuth.currentUser != null;
    }

    fun logOut() {
        if(isLogedin()){
            fbAuth.signOut();
        }
    }
}