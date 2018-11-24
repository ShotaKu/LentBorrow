package lentborrow.cs3231.com.lentborrow.customCell.bookCell

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.book_customcell.view.*
import lentborrow.cs3231.com.lentborrow.R
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController

import lentborrow.cs3231.com.lentborrow.controller.database.book.Book

class BookAdapter(val requests: ArrayList<Book>): RecyclerView.Adapter<BookAdapter.ViewHolder>(){
    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.book_customcell,parent,false)
        val newView = ViewHolder(v)
        return newView
    }

    override fun onBindViewHolder(viewHolder: BookAdapter.ViewHolder, p1: Int) {
        var name = requests[p1].name
        var tType = requests[p1].tradeType;
        if(tType == "for both lenting and trading")
            tType = "lending/trading";
        viewHolder.id = requests[p1].id;
        viewHolder.bookName.setText(name);
        viewHolder.tType.setText(tType)
        viewHolder.itemView.setOnClickListener {
            val amCon = ActivityMigrationController()
            amCon.setBookDetail(viewHolder.itemView.context)
                    .pass("bookID",viewHolder.id)
                    .go();
        }
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val bookName = v.bookName_bookCell
        val tType = v.tradeType_bookCell
        var id = "";
    }
}