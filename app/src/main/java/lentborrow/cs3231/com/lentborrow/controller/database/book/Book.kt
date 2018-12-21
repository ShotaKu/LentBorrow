package lentborrow.cs3231.com.lentborrow.controller.database.book

import com.google.firebase.database.PropertyName
import lentborrow.cs3231.com.lentborrow.controller.database.DatabaseForm

class Book() {
    var id = ""
    var category = ""
    var imageURL = ""
    var isBorrowed = false
    var isUsedForRequest = false
    var lentBy = ""
    var locate = ""
    var name = ""
    var requester = ""
    var tradeType = ""


<<<<<<< HEAD
=======


>>>>>>> 053d92e2f795e298b8040ded6178a2ef6024a07a
    constructor(id: String, category: String, imageURL: String, isBorrowed: Boolean, isUsedForRequest: Boolean
                , lentBy: String, locate: String, name: String, reqester: String, tradeType: String) : this() {
        this.id = id
        this.category = category
        this.imageURL = imageURL
        this.isBorrowed = isBorrowed
        this.isUsedForRequest = isUsedForRequest
        this.lentBy = lentBy
        this.locate = locate
        this.name = name
        this.requester = requester
        this.tradeType = tradeType
    }

    constructor(category: String, imageURL: String, isBorrowed: Boolean, isUsedForRequest: Boolean
                , lentBy: String, locate: String, name: String, reqester: String, tradeType: String) : this() {
        this.category = category
        this.imageURL = imageURL
        this.isBorrowed = isBorrowed
        this.isUsedForRequest = isUsedForRequest
        this.lentBy = lentBy
        this.locate = locate
        this.name = name
        this.requester = requester
        this.tradeType = tradeType
    }

    fun getDatabaseForm(): DatabaseForm {
        return databaseForm(category, name, imageURL, isBorrowed, isUsedForRequest
                , lentBy, locate, requester, tradeType)
    }

    class databaseForm() : DatabaseForm() {
        var category: String = ""
        var name: String = ""
        var image: String = ""

        @set:PropertyName("isBorrowed")
        @get:PropertyName("isBorrowed")
        var isBorrowed: Boolean = false

        @set:PropertyName("isUsedForRequest")
        @get:PropertyName("isUsedForRequest")
        var isUsedForRequest: Boolean = false

        var lentBy: String = ""
        var locate: String = ""
        var requester: String = ""
        var tradeType: String = ""

        constructor(category: String, name: String, imageURL: String, isBorrowed: Boolean, isUsedForRequest: Boolean
                    , lentBy: String, locate: String, requester: String, tradeType: String) : this() {
            this.category = category
            this.name = name
            this.image = imageURL
            this.isBorrowed = isBorrowed
            this.isUsedForRequest = isUsedForRequest
            this.lentBy = lentBy
            this.locate = locate
            this.requester = requester
            this.tradeType = tradeType
        }
    }
}