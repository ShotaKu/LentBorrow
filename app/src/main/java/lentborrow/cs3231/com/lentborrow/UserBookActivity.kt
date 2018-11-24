package lentborrow.cs3231.com.lentborrow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_user_book.*
import kotlinx.android.synthetic.main.book_customcell.view.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.customCell.bookCell.BookAdapter
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class UserBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_book)
    }

    override fun onResume() {
        super.onResume()
        val lvCon = LocalValueController(this)
        val id = lvCon.getID();
        val bCon = BookController();
        if(!id.isEmpty()){
            bCon.getBooksByOwnerID(id,{ books -> run {
                val list = bookList_userBooks;
                list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                val bList = BookAdapter(books)
                list.adapter = bList;
            } },{error ->
                MessageController(this).showToast(error.message)
            })
        }
    }
    fun toAddBookPage(view: View){
        ActivityMigrationController().setAddBook(this).go()
    }
//
//    fun createCell(book: Book): View {
//        val factory = LayoutInflater.from(this)
//        val myView = factory.inflate(R.layout.book_customcell, null)
//        myView.bookName_bookCell.setText(book.name)
//        myView.tradeType_bookCell.setText(book.tradeType);
//        return myView;
//    }
}
