package lentborrow.cs3231.com.lentborrow.controller.database.request

import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseForm

class Request() {
    var requestID = "";
    var bookID = ""
    var date = ""
    var ownerID = ""
    var requesterID = ""
    var status = ""
    var time = ""
    var tradeWithID = ""

    constructor(requestID: String, bookID: String, date: String, ownerID: String, requesterID: String, status: String, time: String, tradeWithID: String)
            : this(bookID, date, ownerID, requesterID, status, time, tradeWithID) {
        this.requestID = requestID
    }

    constructor(bookID: String, date: String, ownerID: String, requesterID: String, status: String, time: String, tradeWithID: String) : this() {
        this.bookID = bookID
        this.date = date;
        this.ownerID = ownerID
        this.requesterID = requesterID
        this.status = status;
        this.time = time;
        this.tradeWithID = tradeWithID;
    }

    fun getDatabaseForm(): DatabaseForm {
        return databaseForm(this.bookID, this.date
                , ownerID, requesterID
                , status, time, tradeWithID)
    }

    data class databaseForm(val bookID: String
                            , val date: String
                            , val ownerID: String
                            , val requesterID: String
                            , val status: String
                            , val time: String
                            , val tradeWithID: String) : DatabaseForm()
}