package lentborrow.cs3231.com.lentborrow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_book_detail.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.request.Request
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.generic.ImageDownloader
import lentborrow.cs3231.com.lentborrow.generic.MessageController
import java.util.*
import kotlin.collections.ArrayList


class BookDetailActivity : AppCompatActivity() {

    var bCon = BookController()
    val rCon = RequestController()
    var showedBook: Book? = null
    var userBooks: ArrayList<Book> = arrayListOf()
    val cal = Calendar.getInstance()
    val RESULT_FROM_DATEPICKER = 1
    val RESULT_FROM_TIMEPICKER = 2
    var amController = ActivityMigrationController()
    var bookID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        amController = ActivityMigrationController()

        bookID = intent.getStringExtra("bookID")

        getBook(bookID)
    }

    fun getBook(bookID: String) {

        bCon.getBookByID(bookID, { book ->
            if (book != null) {
                showedBook = book
                showBookDetail(book)
                val lvCon = LocalValueController(this)

                var isUsers = lvCon.getID() == book.lentBy

                if(!isUsers){
                    rCon.getRequestsByBookID(book.id, { requests ->
                        var isRequested = false
                        for (request in requests) {
                            if (request.requesterID == lvCon.getID()) {
                                if (request.status == "wait for check"){
                                    isRequested = true
                                    break
                                }
                            }
                        }
                        switchRequested(isRequested)
                    }, { error ->
                        MessageController(this).showToast("Error when get request information")
                    })
                }
                else{
                    switchUserBook(isUsers)
                }
            } else
                MessageController(this).showToast("Book detail broken")
        }, { error ->
            MessageController(this).showToast("Book not found")
        })
    }

    fun showBookDetail(book: Book) {
        bookName_detail.text = book.name
        tradeType_detail.text = book.tradeType
        bookCategory_detail.text = "Category: " + book.category
        tradeAt_detail.text = "Trade at " + book.locate
        bookOwner_detail.text = "See book owner"
        ownerID = book.lentBy
        val iDown = ImageDownloader(book.imageURL, bookImage_detail)
        iDown.startDownload()
    }

    var ownerID = ""

    private fun switchUserBook(isUsers: Boolean) {
        if (isUsers) {
            showMessage("This is your book")
            editBook_detail.visibility = View.VISIBLE
            sendButton_detail.visibility = View.INVISIBLE
        } else {
            hideMessage()
            editBook_detail.visibility = View.INVISIBLE
            sendButton_detail.visibility = View.VISIBLE

        }
    }

    private fun switchRequested(isRequested: Boolean) {
        if (isRequested) {
            showMessage("You already request the book. \n Wait for owner reaction")
        } else {
            hideMessage()
            setTradeWithSpinner()
            setTradeOnDatePicker()
            setTradeOnTimePicker()
        }
    }

    private fun switchNoBookForRequest(){
        showMessage("You don't have book for sending trade request")
    }

    private fun showMessage(message: String) {
        val userBook = yourbook_detail
        yourbook_detail.text = message
        //val requestForm = requestForm_detail
        userBook.visibility = View.VISIBLE
        //requestForm.visibility = View.INVISIBLE
    }

    private fun hideMessage() {
        val userBook = yourbook_detail
        //val requestForm = requestForm_detail
        userBook.visibility = View.INVISIBLE
        //requestForm.visibility = View.VISIBLE

    }

    fun toUserDetail(view: View) {
        if (showedBook != null) {
            ActivityMigrationController().setUserDetail(this).pass("userID", showedBook!!.lentBy).go()
        } else {
            Toast.makeText(this, "Load user data failed", Toast.LENGTH_LONG)
        }
    }

    private fun setTradeWithSpinner() {
        val spinner = tradeWith_detail
        val lvCon = LocalValueController(this)
        bCon.getBooksByOwnerID(lvCon.getID(), { books ->
            val availableBooks = bCon.FilterByAvailableBook(books)
            if(0<availableBooks.count()){
                userBooks = availableBooks
                val nameList = ArrayList<String>()
                for (book in availableBooks) {
                    nameList.add(book.name)
                }
                val dataAdapter = ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, nameList)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = dataAdapter
            }else{
                MessageController(this).showToast("No books for trade")
                switchNoBookForRequest()
            }
        }, { error ->

        })
    }

    fun toEditBook(view: View){
        ActivityMigrationController()
                .setEditBook(this)
                .pass("bookID",showedBook!!.id)
                .go()
    }

    private fun setTradeOnDatePicker() {
        val text = tradeOnDate_detail
        val target = datePicker

        //target.
        target.setOnClickListener {
            val text = tradeOnDate_detail
            val yea = cal.get(Calendar.YEAR)
            val mon = cal.get(Calendar.MONTH)
            val date = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                text.text = year.toString() + "-" + monthOfYear + "-" + dayOfMonth
            }, yea, mon, date)
            dpd.show()
        }

    }

    private fun setTradeOnTimePicker() {
        val text = tradeOnTime_detail
        val target = timePicker
        target.setOnClickListener {
            val h = cal.get(Calendar.HOUR)
            val m = cal.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hour, min ->
                text.text = hour.toString() + ":" + min.toString()
            }, h, m, false)
            tpd.show()
        }
    }

    fun sendRequest(view: View) {
        val date = tradeOnDate_detail.text.toString()
        val time = tradeOnTime_detail.text.toString()
        val tradeWithIndex = tradeWith_detail.selectedItemPosition
        if (!date.isEmpty()) {
            if (!time.isEmpty()) {
                if (0 <= tradeWithIndex) {
                    val tradeWith = userBooks[tradeWithIndex]
                    val lvCon = LocalValueController(this)
                    val request = Request(showedBook!!.id
                            , date
                            , showedBook!!.lentBy
                            , lvCon.getID()
                            , "wait for check"
                            , time
                            , tradeWith.id)
                    tradeWith.isUsedForRequest = true
                    bCon.change(tradeWith)
                    rCon.create(request)
                    finish()
                }
            }
        }
    }

    fun ToDeleteBook(view: View)
    {

        val lvCon = LocalValueController(this)
        rCon.getRequestsByBookID(bookID, { requests ->
            for (request in requests) {
                bCon.deleteObject("Request/"+request.requestID)
            }
        }, { error ->
            MessageController(this).showToast("Error while getting book request")
        })

        rCon.getRequestsBytradeWithID(bookID, { requests ->
            for (request in requests) {
                bCon.deleteObject("Request/"+request.requestID)
            }
        }, { error ->
            MessageController(this).showToast("Error while getting book request")
        })

      /* bCon.getRequest(bookID, { book ->
            if (book != null) {
                showedBook = book

                val lvCon = LocalValueController(this)
                rCon.getRequestsByBookID(book.id, { requests ->
                    for (request in requests) {
                        if (request.requesterID == lvCon.getID()) {
                            //DELETE
                            bCon.deleteObject("Request/"+request.requestID)
                        }
                    }
                }, { error ->
                    MessageController(this).showToast("Error while getting book request")
                })
            } else
            //NO REQUESTS
                MessageController(this).showToast("Request not found")
        }, { error ->
            MessageController(this).showToast("Book not found")
        })*/


        val id = lvCon.getID()
        if(!id.isEmpty()){
            bCon.getLendingKey(id,{ keys -> run {
                for(k in keys)
                {
                    bCon.DeleteLendingKey(id,k,bookID)
                }
            } },{error ->
                MessageController(this).showToast(error.message)
            })
        }

        bCon.deleteObject("Book/"+bookID)

        finish()

        amController.setMain(this).go()
        //go back to last page.

    }

}


