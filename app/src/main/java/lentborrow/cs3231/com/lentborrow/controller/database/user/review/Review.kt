package lentborrow.cs3231.com.lentborrow.controller.database.user.review

import com.google.firebase.database.DataSnapshot
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseForm

class Review() {
    var id = ""
    var senderID: String = ""
    var postOn: String = ""
    var content: String = ""
    var stars: Int = 0

    constructor(senderID: String, postOn: String, content: String, stars: Int) : this() {
        this.senderID = senderID
        this.postOn = postOn
        this.content = content
        this.stars = stars
    }

    constructor(id: String, senderID: String, postOn: String, content: String, stars: Int) : this(senderID, postOn, content, stars) {
        this.id = id
    }

    fun getDatabaseForm(): DatabaseForm {
        return databaseForm(senderID, postOn, content, stars)
    }

    data class databaseForm(val senderID: String, val postOn: String, val content: String, val stars: Int) : DatabaseForm()

    companion object {
        fun dataSnapshotAdapter(snapShot: DataSnapshot): Review {
            val id = snapShot.key.toString()
            val senderID = snapShot.child("senderID").value.toString()
            val postOn = snapShot.child("postOn").value.toString()
            val content = snapShot.child("content").value.toString()
            val stars = snapShot.child("stars").value.toString().toInt()
            val result = Review(id, senderID, postOn, content, stars)
            return result
        }
    }
}