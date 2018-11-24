package lentborrow.cs3231.com.lentborrow.controller.database.book

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseController
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import java.lang.Boolean.parseBoolean

class BookController(): DatabaseController(){
    fun create(book: Book,userID:String): Book {
        val bookID = this.pushObject("Book",book.getDatabaseForm(),false);
        this.pushString("User/"+userID+"/Lending",bookID);
        book.id = bookID;
        return book;
    }

    fun getBooksByName(name:String, successCallback: (books: ArrayList<Book>) -> Unit   // Unit = void
                       , failedCallback:(error: DatabaseError) -> Unit){

        find("Book",
                { s: DataSnapshot -> searchBookByName(name,s) }
                ,{ snapShots -> successCallback(snapShotBookAdapter(snapShots)) },failedCallback)
    }

    fun getBookByID(id:String, successCallback: (book: Book?) -> Unit   // Unit = void
                     , failedCallback:(error: DatabaseError) -> Unit){
        //@TODO should not search, just refer to book URL directly.
        find("Book",
                { s: DataSnapshot -> searchBookByID(id,s) }
                ,{ snapShots -> run {
                    if(!snapShots.isEmpty())
                        successCallback(snapShotBookAdapter(snapShots)[0])
                    else
                        successCallback(null)
                }}
                ,failedCallback)
    }

    fun getBooksByOwnerID(id:String, successCallback: (books: ArrayList<Book>) -> Unit   // Unit = void
                          , failedCallback:(error: DatabaseError) -> Unit){
        val uCon = UserController();
        uCon.getUserByID(id,{user -> run {
            getBooksByIDs(ArrayList(user.lending),{ books ->
                successCallback(books)
            },{ error ->
                failedCallback(error)
            })
        } },{error -> failedCallback(error) })
    }

    fun getBooksByIDs(id:ArrayList<String>, successCallback: (books: ArrayList<Book>) -> Unit   // Unit = void
                     , failedCallback:(error: DatabaseError) -> Unit){

        find("Book",
                { s: DataSnapshot -> searchBookByID(id,s) }
                ,{ snapShots -> successCallback(snapShotBookAdapter(snapShots)) }
                ,failedCallback)
    }

    final fun snapShotBookAdapter(snapShot: DataSnapshot):Book{
        val id = snapShot.key.toString();
        val category = snapShot.child("category").value.toString()
        val imageURL = snapShot.child("image").value.toString()
        val isBorrowed = parseBoolean(snapShot.child("isBorrowed").value.toString())
        val isUsedForRequest = parseBoolean(snapShot.child("isUsedForRequest").value.toString())
        val lentBy = snapShot.child("lentBy").value.toString()
        val locate = snapShot.child("locate").value.toString()
        val name = snapShot.child("name").value.toString();
        val requester = snapShot.child("requester").value.toString()
        val tradeType  =snapShot.child("tradeType").value.toString()
        return Book(id, category, imageURL, isBorrowed, isUsedForRequest
                , lentBy, locate, name, requester, tradeType)
    }

    fun snapShotBookAdapter(snapShots: ArrayList<DataSnapshot>): ArrayList<Book> {
        val list = ArrayList<Book>()
        for (snapShot in snapShots){
            list.add(snapShotBookAdapter(snapShot))
        }
        return list;
    }

    private fun searchBookByName(keyName: String, snapShot: DataSnapshot):Boolean{
        var result:Boolean = false;

        val name = snapShot.child("name").value.toString();
        val reg = Regex(keyName)
        if(reg.containsMatchIn(name)){
            result = true;
        }

        return result;
    }

    private fun searchBookByID(keyID: ArrayList<String>, snapShot: DataSnapshot):Boolean{
        var result:Boolean = false;

        val id = snapShot.key.toString();
        if(keyID.contains(id)){
            result = true;
        }

        return result;
    }

    private fun searchBookByID(keyID: String, snapShot: DataSnapshot):Boolean{
        var result:Boolean = false;

        val id = snapShot.key.toString();
        if(keyID == id) {
            result = true;

        }

        return result;
    }
}