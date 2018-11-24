package lentborrow.cs3231.com.lentborrow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_search.*
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.customCell.bookCell.BookAdapter

class SearchActivity : AppCompatActivity() {
    var list:RecyclerView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val a = ArrayList<Book>();
        a.add(Book());
        a.add(Book());
        list = searchResultList_search;
        list!!.adapter = BookAdapter(a);
        serchBox_search.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //not thing
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not thing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                search(p0.toString());
            }
        })
    }

    private fun search(key:String){
        val bCon = BookController();

         bCon.getBooksByName(key,{books ->
                 showResult(books);
         },fun (e:DatabaseError){
             Log.d("ERROR", e.message)
         })
    }
    private fun showResult(books:ArrayList<Book>){
        if(list != null){
            list!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            val bAdap = BookAdapter(books)
            list!!.adapter = bAdap;
        }
    }
}
