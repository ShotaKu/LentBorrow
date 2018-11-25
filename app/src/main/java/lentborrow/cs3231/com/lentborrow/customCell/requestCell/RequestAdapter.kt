package lentborrow.cs3231.com.lentborrow.customCell.requestCell

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.request_customcell.view.*
import lentborrow.cs3231.com.lentborrow.R
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.request.Request
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.generic.ImageDownloader

class RequestAdapter(val requests: ArrayList<Request>): RecyclerView.Adapter<RequestAdapter.ViewHolder>(){
    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.request_customcell,parent,false)
/*        v.setOnClickListener{
            var message = v.subtitle.text +":"+ v.title.text
            Toast.makeText(it,message, Toast.LENGTH_LONG).show()
        }*/
        val newView = ViewHolder(v)
        return newView
    }

    override fun onBindViewHolder(viewHolder: RequestAdapter.ViewHolder, p1: Int) {
        val bCon = BookController()
        bCon.getBookByID(requests[p1].bookID,{ book ->
            if(book != null){
                bCon.getBookByID(requests[p1].tradeWithID,{ tradeWithBook ->
                    if(tradeWithBook != null){
                        val uCon = UserController()
                        uCon.getUserByID(requests[p1].requesterID,{ requester ->
                            var name = book!!.name;
                            var tradeWith = tradeWithBook!!.name
                            viewHolder.bookName.text = name;
                            viewHolder.tradeWith.text = "Trade with: "+tradeWith;
                            viewHolder.reqester.text = "Sent by "+ requester.userName
                            viewHolder.tradeDate.text = requests[p1].date + " "+ requests[p1].time
                            viewHolder.itemView.setOnClickListener {
                                //Toast.makeText(this, requests[p1].id+": "+requests[p1].name, Toast.LENGTH_SHORT).show()
                                //TODO go to request activity
                            }
                            val downloadImage = ImageDownloader(tradeWithBook.imageURL,viewHolder.image)
                            downloadImage.startDownload()
                            viewHolder.status.text = requests[p1].status
                            val context = viewHolder.itemView.context;
                            if(requests[p1].status == "accepted"){
                                viewHolder.status.setTextColor(context.resources.getColor(R.color.accepted));
                            }else if(requests[p1].status == "rejected"){
                                viewHolder.status.setTextColor(context.resources.getColor(R.color.rejected));
                            }else{
                                viewHolder.status.setTextColor(context.resources.getColor(R.color.pedding));
                            }
                        },{ error ->
                            Toast.makeText(viewHolder.itemView.context,"get user information failed",Toast.LENGTH_LONG).show()
                        })
                    }
                },{error ->
                    Toast.makeText(viewHolder.itemView.context,"get book information failed",Toast.LENGTH_LONG).show()

                })
            }
        },{
            Toast.makeText(viewHolder.itemView.context,"get book information failed",Toast.LENGTH_LONG).show()

        })
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val bookName = v.bookName_requestCell
        val tradeWith = v.tradeWith_requestCell
        val reqester = v.requesterName_requestCell
        val tradeDate = v.tradingDate_requestCell
        val status = v.requestStatus_requestCell
        val image = v.tradeWithImage_requestCell
    }
}