package lentborrow.cs3231.com.lentborrow.customCell.requestCell

import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import java.util.*

class Request() {
    public var requestID: String = "N/A"
        get
        private set
    public var book: Book = Book()
        get
        private set
    public var requesterName: String = "Requester Name"
        get
        private set
    public var tradeWith: Book = Book()
        get
        private set
    public var tradeOn:Date = Date()
        get
        private set;

    constructor(requestID: String,book:Book ,requesterName:String,tradeWith:Book, tradeOn:Date) : this() {
        this.requestID = requestID;
        this.requesterName = requesterName;
        this.book = book;
        this.tradeWith = tradeWith;
        this.tradeOn = tradeOn;
    }

}