package lentborrow.cs3231.com.lentborrow.controller.database

import android.provider.ContactsContract
import com.google.firebase.database.*
import lentborrow.cs3231.com.lentborrow.controller.database.user.User
import com.google.firebase.database.DataSnapshot
import java.lang.reflect.Array


open class DatabaseController(){
    var database: FirebaseDatabase = FirebaseDatabase.getInstance();

    protected fun get(path:String, successCallback: (snapShot:DataSnapshot) -> Unit   // Unit = void
            , failedCallback:(error: DatabaseError) -> Unit){
        val myRef = database.getReference(path);
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //val value = dataSnapshot.getValue(String::class.java)
                //Log.d(TAG, "Value is: " + value!!)
                successCallback(dataSnapshot);
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                failedCallback(error);
            }
        })
    }

    fun setString(path:String,value:String){
        setObject(path,value);
    }

    fun setObject(path: String,value: Any) {
        val myRef = database.getReference(path)
        myRef.setValue(value);
    }
    fun pushObject(path:String,value: DatabaseForm, withPath:Boolean = true):String{
        val myRef = database.getReference(path)
        val userPath:DatabaseReference = myRef.push()
        setObject(path+ "/" + userPath.key!!,value);
        if(withPath)
            return path + userPath.key!!
        else
            return userPath.key!!
    }
    protected fun pushString(path:String,value: String):String{
        val myRef = database.getReference(path)
        val userPath:DatabaseReference = myRef.push()
        setObject(path+ "/" + userPath.key!!,value);
        return path + userPath.key!!
    }
    fun find(path:String, searchCallback: (snapShot:DataSnapshot) -> Boolean
             , successCallback: (snapShot:ArrayList<DataSnapshot>) -> Unit   // Unit = void
             , notFoundCallback:(error: DatabaseError) -> Unit){
        val myRef = database.getReference(path);
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = ArrayList<DataSnapshot>()
                for (snapshot in dataSnapshot.children) {
                    val res = searchCallback(snapshot);
                    if(res){
                        result.add(snapshot);
                    }
                }
                if(result.isEmpty())
                    notFoundCallback(DatabaseError.fromCode(DatabaseError.UNAVAILABLE))
                else
                    successCallback(result);
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                notFoundCallback(error);
            }
        })
    }
    fun finds(path:String, searchCallback: (snapShot:DataSnapshot) -> Boolean
             , successCallback: (snapShot:DataSnapshot) -> Unit   // Unit = void
             , notFoundCallback:(error: DatabaseError) -> Unit){
        val myRef = database.getReference(path);
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = ArrayList<DataSnapshot>(arrayListOf(null))
                for (snapshot in dataSnapshot.children) {
                    val result = searchCallback(snapshot!!);
                    if(result){
                        successCallback(snapshot);
                        return;
                    }
                }
                notFoundCallback(DatabaseError.fromCode(DatabaseError.UNAVAILABLE))
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                notFoundCallback(error);
            }
        })
    }
}