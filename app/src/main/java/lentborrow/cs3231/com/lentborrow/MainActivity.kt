package lentborrow.cs3231.com.lentborrow

import android.content.res.Configuration
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import android.os.Bundle
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.auth.LoginController
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseController
import lentborrow.cs3231.com.lentborrow.generic.Option

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    var amController = ActivityMigrationController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        amController = ActivityMigrationController()



        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        //setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_item_one -> requestBox()
            R.id.nav_item_two -> search()
            R.id.nav_item_three -> user()
            R.id.nav_item_four -> books()
            R.id.nav_item_five -> log()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun user(){
        amController.setUserDetail(this).pass("userID","-LN0-bh6HggS-bPicKJY").go()
    }

    fun search(){
        amController.setSearchActivity(this).go()
    }

    fun requestBox() {
        var intent = Intent(this, RequestBoxActivity::class.java)
        startActivity(intent)
    }

    fun books(){
        amController.setUserBook(this).go()
    }

    fun log(){
        var log = LoginController()
        log.logOut()
        amController.setLoginActivity(this).go()
        finish()

    }
    /*var amController = ActivityMigrationController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dCon = DatabaseController()
        val obj = test("Test post",1)
        dCon.setObject("Test",obj)
        amController = ActivityMigrationController()
    }

    fun requestBox(view: View) {
        var intent = Intent(this, RequestBoxActivity::class.java)
        startActivity(intent)
    }
    fun register(view: View){
        amController.setRegistrationActivity(this).go()
    }
    fun login(view: View){
        amController.setLoginActivity(this).go()
    }
    fun search(view: View){
        amController.setSearchActivity(this).go()
    }
    fun user(view: View){
        amController.setUserDetail(this).pass("userID","-LN0-bh6HggS-bPicKJY").go()
    }
    fun books(view: View){
        amController.setUserBook(this).go()
    }

    fun log(view: View){
        amController.setLoginActivity(this).go()
        finish()
    }
    data class test(val message: String, val num: Int)*/
}
