package lentborrow.cs3231.com.lentborrow.controller.database.user.review

import com.google.firebase.database.DataSnapshot

class Review(){
    var id = ""
    var postFrom:String = ""
    var postOn:String = ""
    var content:String = ""
    var stars:Int = 0;

    constructor(postFrom:String, postOn:String, content:String,stars:Int):this(){
        this.postFrom = postFrom
        this.postOn = postOn
        this.content = content;
    }
    constructor(id:String,postFrom:String, postOn:String, content:String,stars:Int):this(postFrom, postOn, content, stars){
        this.id = id;
    }

    companion object {
        fun dataSnapshotAdapter(snapShot: DataSnapshot):Review{
            val id = snapShot.key.toString()
            val postFrom = snapShot.child("postFrom").value.toString()
            val postOn = snapShot.child("postOn").value.toString()
            val content = snapShot.child("content").value.toString()
            val stars = snapShot.child("stars").value.toString().toInt()
            val result = Review(id,postFrom, postOn, content, stars)
            return result;
        }
    }
}