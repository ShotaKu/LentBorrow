package lentborrow.cs3231.com.lentborrow.controller.database.user

import android.provider.ContactsContract
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseController
import lentborrow.cs3231.com.lentborrow.controller.database.user.review.Review

class UserController : DatabaseController(){
    fun create(user:User):User{
        val userID = this.pushObject("User",user.getDatabaseForm())
        user.userID = userID
        return user
    }

    fun update(user: User):User{
        val userID = user.userID
        setObject("User/"+userID,user.getDatabaseForm())
        return user
    }

    fun getUserByEmail(email:String, successCallback: (user: User) -> Unit   // Unit = void
    , failedCallback:(error: DatabaseError) -> Unit){

        finds("User",
                { s:DataSnapshot -> searchUserByEmail(email,s) }
                ,{ snapShot -> run {
                    successCallback(dataSnapshotAdapter(snapShot))
                }}
                ,failedCallback)
    }
    fun getUserByEmailOnce(email:String, successCallback: (user: User) -> Unit   // Unit = void
                       , failedCallback:(error: DatabaseError) -> Unit){

        OnceFinds("User",
                { s:DataSnapshot -> searchUserByEmail(email,s) }
                ,{ snapShot -> run {
            successCallback(dataSnapshotAdapter(snapShot))
        }}
                ,failedCallback)
    }

    fun getUserByID(id:String, successCallback: (user: User) -> Unit   // Unit = void
                    , failedCallback:(error: DatabaseError) -> Unit){
        finds("User",
                { s:DataSnapshot -> searchUserByID(id,s) }
                ,fun(s:DataSnapshot){
                    successCallback(dataSnapshotAdapter(s))
                }
                ,failedCallback)
    }

    fun createReview(userID:String,review:Review):Review{
        val reviewID = this.pushObject("User/"+userID+"/Review",review.getDatabaseForm(),false)
        review.id = reviewID
        return review
    }

    fun searchUserByEmail(email: String, snapShot: DataSnapshot):Boolean{
        var result:Boolean = false

        val mail = snapShot.child("email").value.toString()
        if(email == mail){
            result = true
        }

        return result
    }

    fun dataSnapshotAdapter(snapShot: DataSnapshot):User{
        val userID:String = snapShot.key.toString()
        val userName:String = snapShot.child("userName").value.toString()
        val email:String = snapShot.child("email").value.toString()
        val name:String = snapShot.child("name").value.toString()
        val lending:ArrayList<String> = ArrayList()
        val lendingKey:ArrayList<String> = ArrayList()
        val reviews:ArrayList<Review> = arrayListOf()
        for(book in snapShot.child("Lending").children){
            lending.add(book.value.toString())
        }
        for(book in snapShot.child("Lending").children){
            lendingKey.add(book.key.toString())
        }
        for(review in snapShot.child("Review").children){
            reviews.add(Review.dataSnapshotAdapter(review))
        }

        return User(userID,userName,email,name,lending,lendingKey,reviews)
    }

    fun searchUserByID(id: String, snapShot: DataSnapshot):Boolean{
        var result:Boolean = false

        val uID = snapShot.key.toString()
        if(id == uID){
            result = true
        }

        return result
    }

}