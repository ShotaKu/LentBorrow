package lentborrow.cs3231.com.lentborrow

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseController

class MainActivity : AppCompatActivity() {

    var amController = ActivityMigrationController();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dCon = DatabaseController();
        val obj = test("Test post",1)
        dCon.setObject("Test",obj);
        amController = ActivityMigrationController();
    }

    fun requestBox(view: View) {
        var intent = Intent(this, RequestBoxActivity::class.java)
        startActivity(intent);
    }
    fun register(view: View){
        amController.setRegistrationActivity(this).go();
    }
    fun login(view: View){
        amController.setLoginActivity(this).go();
    }
    fun search(view: View){
        amController.setSearchActivity(this).go();
    }
    fun user(view: View){
        amController.setUserDetail(this).pass("userID","-LN0-bh6HggS-bPicKJY").go();
    }
    fun books(view: View){
        amController.setUserBook(this).go()
    }
    data class test(val message: String, val num: Int)
}
