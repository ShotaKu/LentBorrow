package lentborrow.cs3231.com.lentborrow.controller.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import lentborrow.cs3231.com.lentborrow.*

open class ActivityMigrationBuilder{
    var intent: Intent? = null
    var context: Context? = null

    protected constructor()
    protected constructor(context: Context,activityName: String){
        set(context,activityName)
    }

    protected val activityIndex = mutableMapOf(
            "Login" to LoginActivity::class.java
            ,"Register" to RegistrationActivity::class.java
            ,"BookDetail" to BookDetailActivity::class.java
            ,"RequestBox" to RequestBoxActivity::class.java
            , "Search" to SearchActivity::class.java
            ,"BookDetail" to BookDetailActivity::class.java
            ,"UserDetail" to UserDetailActivity::class.java
            ,"AddBook" to AddBookActivity::class.java
            ,"UserBook" to UserBookActivity::class.java
            //@ToDo: Fook Please edit as EditBookActivity::class.java when you finish create the activity .kt
            ,"EditBook" to UserBookActivity::class.java
            ,"RequestLog" to RequestLogActivity::class.java
    )

    protected fun set(context: Context,activityName: String){
        val act:Class<out AppCompatActivity>? = activityIndex.get(activityName)
        if (act != null) {
            intent = Intent(context, act)
            this.context = context
        }else{
            intent = Intent()
        }
    }

    fun pass(key:String, value:String): ActivityMigrationBuilder {
        if(intent != null)
            intent!!.putExtra(key,value)
        return this
    }

    fun go(){
        if(context != null && intent != null)
            context!!.startActivity(intent!!)
    }
}