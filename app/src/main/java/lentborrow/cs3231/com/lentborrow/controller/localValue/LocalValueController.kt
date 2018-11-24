package lentborrow.cs3231.com.lentborrow.controller.localValue

import android.content.Context
import android.content.SharedPreferences

class LocalValueController(context: Context){
    final val localStrageID = "LENTBORROW_STORAGE"
    var local:SharedPreferences? = null;
    var editor:SharedPreferences.Editor? = null;

    init {
        this.local = context.getSharedPreferences(localStrageID, 0)
        this.editor = local!!.edit();
    }

    private fun getString(key:String):String{
        return local!!.getString(key,"");
    }

    fun getBool(key: String):Boolean{
        return local!!.getBoolean(key,false);
    }

    fun setBool(key: String,value:Boolean){
        editor!!.putBoolean(key,value);
        editor!!.commit();
    }

    private fun setString(key: String, value: String){
        editor!!.putString(key,value);
        editor!!.commit();
    }

    fun getEmail(): String {
        return getString("Email")
    }

    fun setEmail(email: String) {
        setString("email",email)
    }

    fun setPassword(password:String){
        setString("password", password);
    }

    fun getPassword():String{
        return getString("password")
    }

    fun setID(userID: String) {
        setString("userID",userID);
    }
    fun getID():String {
        return getString("userID");
    }
}