package lentborrow.cs3231.com.lentborrow.controller.database.request

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseController

class RequestController :DatabaseController(){

    fun create(request: Request):Request{
        val requestID = this.pushObject("Request",request.getDatabaseForm(),false)
        request.requestID = requestID
        return request
    }

    fun update(request: Request):Request{
        val requestID = request.requestID
        setObject("Request/"+requestID,request.getDatabaseForm())
        return  request
    }

    fun getRequestsByRequesterID(requesterID:String,successCallback: (requests:ArrayList<Request>) -> Unit   // Unit = void
                                , failedCallback:(error: DatabaseError) -> Unit){
        find("Request",{ snapShot: DataSnapshot ->
            searchRequestByRequesterID(requesterID,snapShot)
        },{snapShots ->
            //@TODO
            successCallback(snapShotRequestAdapter(snapShots))
        },{error ->
            //@TODO
            failedCallback(error)
        })
    }

    fun getRequestsByBookID(bookID: String,successCallback: (requests:ArrayList<Request>) -> Unit   // Unit = void
                           , failedCallback:(error: DatabaseError) -> Unit){
        //@TODO
        find("Request",{ snapShot: DataSnapshot ->
            searchRequestByBookID(bookID,snapShot)
        },{snapShots ->
            //@TODO
            successCallback(snapShotRequestAdapter(snapShots))
        },{error ->
            //@TODO
            failedCallback(error)
        })
    }

    fun getRequestsBytradeWithID(bookID: String,successCallback: (requests:ArrayList<Request>) -> Unit   // Unit = void
                            , failedCallback:(error: DatabaseError) -> Unit){
        //@TODO
        find("Request",{ snapShot: DataSnapshot ->
            searchRequestBytradeWithID(bookID,snapShot)
        },{snapShots ->
            //@TODO
            successCallback(snapShotRequestAdapter(snapShots))
        },{error ->
            //@TODO
            failedCallback(error)
        })
    }

    fun getRequestsByOwnerID(ownerID:String,successCallback:  (requests:ArrayList<Request>) -> Unit    // Unit = void
                            , failedCallback:(error: DatabaseError) -> Unit){
        //@TODO
        find("Request",{ snapShot: DataSnapshot ->
            searchRequestByOwnerID(ownerID,snapShot)
        },{snapShots ->
            //@TODO
            successCallback(snapShotRequestAdapter(snapShots))
        },{error ->
            //@TODO
            failedCallback(error)
        })
    }





    fun getRequestByRequestID(requestID:String,successCallback: (request:Request) -> Unit   // Unit = void
                              , failedCallback:(error: DatabaseError) -> Unit){
        find("Request",{ snapShot: DataSnapshot ->
            searchRequestByID(requestID,snapShot)
        },{snapShots ->
            if(!snapShots.isEmpty())
                successCallback(snapShotRequestAdapter(snapShots)[0])
            else
                successCallback(Request())
        },{error ->
            failedCallback(error)
        })
    }

    fun filterByAcceptedRequests(requests:ArrayList<Request>):ArrayList<Request>{
        return filterBy(requests,"accepted")
    }
    private fun searchRequestByID(requestID:String,snapshot: DataSnapshot):Boolean{
        return requestID == snapshot.key.toString()
    }
    fun filterByRejectedRequests(requests:ArrayList<Request>):ArrayList<Request>{
        return filterBy(requests,"rejected")
    }

    fun filterByWaitingRequests(requests:ArrayList<Request>):ArrayList<Request>{
        return filterBy(requests,"wait for check")
    }

    private fun filterBy(requests:ArrayList<Request>,status:String):ArrayList<Request>{
        val result = arrayListOf<Request>()

        for (request in requests){
            if(request.status == status)
                result.add(request)
        }

        return result
    }



    private fun searchRequestByRequesterID(requesterID: String,snapshot: DataSnapshot):Boolean{
        return search("requesterID", requesterID, snapshot)
    }

    private fun searchRequestByBookID(bookID:String,snapshot: DataSnapshot):Boolean{
        return search("bookID", bookID, snapshot)
    }

    private fun searchRequestByOwnerID(ownerID:String,snapshot: DataSnapshot):Boolean{
        return search("ownerID", ownerID, snapshot)
    }

    private fun searchRequestBytradeWithID(tradeWithID:String,snapshot: DataSnapshot):Boolean{
        return search("tradeWithID", tradeWithID, snapshot)
    }

    private fun search(key: String, value:String, snapshot: DataSnapshot):Boolean{
        val id = snapshot.child(key).value.toString()
        return (id == value)
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
        return list
    }
}