package lentborrow.cs3231.com.lentborrow.controller.database.user

import android.provider.ContactsContract
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseForm
import lentborrow.cs3231.com.lentborrow.controller.database.user.review.Review

class User(){
    var userID:String = ""
    var userName:String = ""
    var email:String = ""
    var name:String = ""
    var lending:List<String> = emptyList()
<<<<<<< HEAD
=======
    var lendingKey:List<String> = emptyList()
>>>>>>> 053d92e2f795e298b8040ded6178a2ef6024a07a
    var reviews:ArrayList<Review> = ArrayList()

    constructor(userName:String,email:String):this(){
        this.userName = userName
        this.email = email
        this.name = "Anonymous user"
    }

<<<<<<< HEAD
    constructor(userID:String, userName: String,email: String,name:String,lending:List<String>):this(){
=======
    constructor(userID:String, userName: String,email: String,name:String,lending:List<String>,lendingKey: List<String>):this(){
>>>>>>> 053d92e2f795e298b8040ded6178a2ef6024a07a
        this.userName = userName
        this.email = email
        this.name = name
        this.lending = lending
<<<<<<< HEAD
        this.userID = userID
    }
    constructor(userID:String, userName: String,email: String,name:String,lending:List<String>, reviews: ArrayList<Review>):this(userID,userName,email,name,lending){
=======
        this.lendingKey = lendingKey
        this.userID = userID
    }
    constructor(userID:String, userName: String,email: String,name:String,lending:List<String>,lendingKey:List<String>, reviews: ArrayList<Review>):this(userID,userName,email,name,lending,lendingKey){
>>>>>>> 053d92e2f795e298b8040ded6178a2ef6024a07a
        this.reviews = reviews
    }

    fun getDatabaseForm():DatabaseForm{
<<<<<<< HEAD
        return databaseForm(userName,email,name,lending,reviews)
    }

    data class databaseForm(val userName: String, val email: String, val name:String,val lending:List<String>, val reviews:List<Review>):DatabaseForm()
=======
        return databaseForm(userName,email,name,lending,lendingKey,reviews)
    }

    data class databaseForm(val userName: String, val email: String, val name:String,val lending:List<String>,val lendingKey:List<String>, val reviews:List<Review>):DatabaseForm()
>>>>>>> 053d92e2f795e298b8040ded6178a2ef6024a07a

}