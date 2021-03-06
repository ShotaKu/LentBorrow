package lentborrow.cs3231.com.lentborrow.customCell.requestCell

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.new_request_customcell.view.*
import lentborrow.cs3231.com.lentborrow.R
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.request.Request
import lentborrow.cs3231.com.lentborrow.generic.ImageDownloader
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class NewRequestAdapter(val requests: ArrayList<Request>) : RecyclerView.Adapter<NewRequestAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.new_request_customcell, parent, false)
        val newView = ViewHolder(v)
        return newView
    }

    override fun onBindViewHolder(viewHolder: NewRequestAdapter.ViewHolder, cellNumber: Int) {
        val request = requests[cellNumber]
        val mCon = MessageController(viewHolder.itemView.context)

        viewHolder.bCon.getBookByID(request.bookID, { book ->
            if (book != null) {
                viewHolder.bookName.text = book.name
                ImageDownloader(book.imageURL, viewHolder.bookImage).startDownload {
                    viewHolder.load1.visibility = View.INVISIBLE
                }
                viewHolder.bCon.getBookByID(request.tradeWithID, { tradeWith ->

                    if (tradeWith != null) {
                        viewHolder.tradeWithName.text = tradeWith.name
                        viewHolder.date.text = request.date
                        viewHolder.time.text = request.time

                        ImageDownloader(tradeWith.imageURL, viewHolder.tradeWithImage).startDownload {
                            viewHolder.load2.visibility = View.INVISIBLE
                        }
                        viewHolder.itemView.setOnClickListener {
                            //ActivityMigrationController().setRequestBoxActivity()
                            ActivityMigrationController()
                                    .setRequestDetail(viewHolder.itemView.context)
                                    .pass("requestID", request.requestID)
                                    .go()
                        }
                    }
                }, {
                    mCon.showToast("Failed to load book information")
                })
            }

        }, {
            mCon.showToast("Failed to load book information")
        })
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val bookName = v.bookName_newReqCell
        val tradeWithName = v.tradeWithName_newReqCell
        val date = v.tradeDate_newReqCell
        val time = v.tradeTime_newReqCell
        val bookImage = v.bookImage_newReqCel
        val tradeWithImage = v.tradeWithImage_newReqCell
        val bCon = BookController()
        val load1 = v.loading1_newReqCell
        val load2 = v.loading2_reqestCell

    }
}