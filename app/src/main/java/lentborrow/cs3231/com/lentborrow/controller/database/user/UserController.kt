package lentborrow.cs3231.com.lentborrow.controller.database.user

import android.provider.ContactsContract
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseController

class UserController(): DatabaseController(){
    fun create(user:User):User{
        val userID = this.pushObject("User",user.getDatabaseForm());
        user.userID = userID;
        return user;
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

    fun getUserByID(id:String, successCallback: (user: User) -> Unit   // Unit = void
                    , failedCallback:(error: DatabaseError) -> Unit){
        finds("User",
                { s:DataSnapshot -> searchUserByID(id,s) }
                ,fun(s:DataSnapshot){
                    successCallback(dataSnapshotAdapter(s))
                }
                ,failedCallback)
    }

    fun searchUserByEmail(email: String, snapShot: DataSnapshot):Boolean{
        var result:Boolean = false;

        val mail = snapShot.child("email").value.toString();
        if(email == mail){
            result = true;
        }

        return result;
    }

    fun dataSnapshotAdapter(snapShot: DataSnapshot):User{
        val userID:String = snapShot.key.toString();
        val userName:String = snapShot.child("userName").value.toString();
        val email:String = snapShot.child("email").value.toString();
        val name:String = snapShot.child("name").value.toString();
        val lending:ArrayList<String> = ArrayList();
        for(book in snapShot.child("Lending").children){
            lending.add(book.value.toString());
        }

        return User(userID,userName,email,name,lending);
    }

    fun searchUserByID(id: String, snapShot: DataSnapshot):Boolean{
        var result:Boolean = false;

        val uID = snapShot.key.toString();
        if(id == uID){
            result = true;
        }

        return result;
    }

}