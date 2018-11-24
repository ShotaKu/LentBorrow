package lentborrow.cs3231.com.lentborrow.controller.database.user

import android.provider.ContactsContract
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseForm

class User(){
    var userID:String = "";
    var userName:String = "";
    var email:String = "";
    var name:String = "";
    var lending:List<String> = emptyList();

    constructor(userName:String,email:String):this(){
        this.userName = userName;
        this.email = email;
        this.name = "Anonymous user"
    }

    constructor(userID:String, userName: String,email: String,name:String,lending:List<String>):this(){
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.lending = lending;
        this.userID = userID;
    }

    fun getDatabaseForm():DatabaseForm{
        return databaseForm(userName,email,name,lending)
    }
    data class databaseForm(val userName: String, val email: String, val name:String,val lending:List<String>):DatabaseForm()

}