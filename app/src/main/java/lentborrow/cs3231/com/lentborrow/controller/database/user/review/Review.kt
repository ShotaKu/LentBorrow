package lentborrow.cs3231.com.lentborrow.controller.database.user.review

import com.google.firebase.database.DataSnapshot

class Review(){
    var id = ""
    var postFrom:String = ""
    var postOn:String = ""
    var content:String = ""

    constructor(postFrom:String, postOn:String, content:String):this(){
        this.postFrom = postFrom
        this.postOn = postOn
        this.content = content;
    }
    constructor(id:String,postFrom:String, postOn:String, content:String):this(postFrom, postOn, content){
        this.id = id;
    }

    companion object {
        fun dataSnapshotAdapter(snapShot: DataSnapshot):Review{
            val id = snapShot.key.toString()
            val postFrom = snapShot.child("postFrom").value.toString()
            val postOn = snapShot.child("postOn").value.toString()
            val content = snapShot.child("content").value.toString()
            val result = Review(id,postFrom,postOn, content)
            return result;
        }
    }
}