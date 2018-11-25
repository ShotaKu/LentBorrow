package lentborrow.cs3231.com.lentborrow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_book_detail.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.generic.ImageDownloader
import lentborrow.cs3231.com.lentborrow.generic.MessageController
import java.util.*
import kotlin.collections.ArrayList


class BookDetailActivity : AppCompatActivity() {

    var bCon = BookController()
    var showedBook:Book? = null
    var userBooks:ArrayList<Book> = arrayListOf();
    val cal = Calendar.getInstance()
    val RESULT_FROM_DATEPICKER = 1;
    val RESULT_FROM_TIMEPICKER = 2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        var bookID = intent.getStringExtra("bookID");
        getBook(bookID)
    }

    fun getBook(bookID:String){

        bCon.getBookByID(bookID,{ book-> run {
            if(book != null){
                showedBook = book;
                showBookDetail(book);
                val lvCon = LocalValueController(this)
                switchUserBook(lvCon.getID() == book.lentBy)
            }else
                MessageController(this).showToast("Book detail broken");
        }},{ error ->
            MessageController(this).showToast("Book not found");
        })
    }

    fun showBookDetail(book: Book){
        bookName_detail.text = book.name;
        tradeType_detail.text =  book.tradeType;
        bookCategory_detail.text = "Category: " + book.category;
        tradeAt_detail.text = "Trade at " + book.locate;
        bookOwner_detail.text = "See book owner"
        ownerID = book.lentBy;
        val iDown = ImageDownloader(book.imageURL,bookImage_detail)
        iDown.startDownload()
    }

    var ownerID = "";

    fun switchUserBook(isUsers:Boolean){
        val userBook = yourbook_detail
        val requestForm = requestForm_detail
        if(isUsers){
            userBook.visibility = View.VISIBLE
            requestForm.visibility = View.INVISIBLE
        }else{
            userBook.visibility = View.INVISIBLE
            requestForm.visibility = View.VISIBLE
            setTradeWithSpinner()
            setTradeOnDatePicker()
            setTradeOnTimePicker()
        }
    }
    fun toUserDetail(view: View){
        if(showedBook != null){
            ActivityMigrationController().setUserDetail(this).pass("userID",showedBook!!.lentBy).go()
        }
        else{
            Toast.makeText(this,"Load user data failed", Toast.LENGTH_LONG)
        }
    }

    fun setTradeWithSpinner(){
        val spinner = tradeWith_detail
        val lvCon = LocalValueController(this);
        bCon.getBooksByOwnerID(lvCon.getID(),{books ->
            userBooks = books;
            val nameList = ArrayList<String>()
            for(book in books){
                nameList.add(book.name);
            }
            val dataAdapter = ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, nameList)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = dataAdapter;
        },{ error ->

        })

        spinner.adapter
    }

    fun setTradeOnDatePicker(){
        val text = tradeOnDate_detail
        val target = datePicker

        //target.
        target.setOnClickListener(){
            val text = tradeOnDate_detail
            val yea = cal.get(Calendar.YEAR)
            val mon = cal.get(Calendar.MONTH)
            val date = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                text.text = year.toString() + "/" + monthOfYear + "/" + dayOfMonth
            }, yea, mon, date)
            dpd.show()
        }
        
    }

    fun setTradeOnTimePicker(){
        val text = tradeOnTime_detail
        val target = timePicker
        target.setOnClickListener(){
            val h = cal.get(Calendar.HOUR)
            val m = cal.get(Calendar.MINUTE)

            val tpd = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{view,hour,min ->
                text.text = hour.toString()+":"+min.toString()
            },h,m,false)
            tpd.show()
        }
    }

    fun sendRequest(view:View){
        //@TODO make it
    }
}


