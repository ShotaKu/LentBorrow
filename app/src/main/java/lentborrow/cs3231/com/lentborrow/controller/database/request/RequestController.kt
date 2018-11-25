package lentborrow.cs3231.com.lentborrow.controller.database.request

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseController

class RequestController():DatabaseController(){

    fun create(request: Request):Request{
        val requestID = this.pushObject("Request",request.getDatabaseForm(),false)
        request.requestID = requestID
        return request;
    }

    fun getRequestsByRequesterID(requesterID:String,successCallback: (request:ArrayList<Request>) -> Unit   // Unit = void
                                , failedCallback:(error: DatabaseError) -> Unit){
        find("Request",{snapShot: DataSnapshot ->
            searchRequestByRequesterID(requesterID,snapShot)
        },{snapShots ->
            //@TODO
        },{error ->
            //@TODO
        })
    }

    fun getRequestsByBookID(bookID: String,successCallback: (request:Request?) -> Unit   // Unit = void
                           , failedCallback:(error: DatabaseError) -> Unit){
        //@TODO
    }

    fun getRequestByRequestID(requestID:String,successCallback: (request:Request) -> Unit   // Unit = void
                              , failedCallback:(error: DatabaseError) -> Unit){
        //@TODO
    }

    fun searchRequestByRequesterID(requesterID: String,snapshot: DataSnapshot):Boolean{
        val id = snapshot.child("requesterID").value.toString()
        return (id == requesterID)
    }

    private fun snapShotRequestAdapter(snapshot: DataSnapshot):Request{
        return Request(snapshot.key.toString()
                ,snapshot.child("bookID").value.toString()
                ,snapshot.child("date").value.toString()
                ,snapshot.child("ownerID").value.toString()
                ,snapshot.child("requesterID").value.toString()
                ,snapshot.child("status").value.toString()
                ,snapshot.child("time").value.toString()
                ,snapshot.child("tradeWithID").value.toString())
    }

    private fun snapShotRequestAdapter(snapShots: ArrayList<DataSnapshot>): ArrayList<Request> {
        val list = ArrayList<Request>()
        for (snapShot in snapShots) {
            list.add(snapShotRequestAdapter(snapShot))
        }
        return list;
    }
}