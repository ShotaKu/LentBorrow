package lentborrow.cs3231.com.lentborrow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_request_box.*
import kotlinx.android.synthetic.main.activity_request_detail.*
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.request.Request
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.NewRequestAdapter
import lentborrow.cs3231.com.lentborrow.generic.ImageDownloader
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class RequestDetailActivity : AppCompatActivity() {

    var requestData:Request? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)
        title = "RequestDetail"
        val mCon = MessageController(this)

        val requestID = intent.getStringExtra("requestID");
        val rCon = RequestController()
        rCon.getRequestByRequestID(requestID,{ request ->
            if(request != null){
                requestData = request
                val bCon = BookController()
                bCon.getBookByID(request.bookID,{book ->
                    if(book != null){
                        bCon.getBookByID(request.tradeWithID,{ tradeWithBook ->
                            if(tradeWithBook != null){
                                val bookImageDownload = ImageDownloader(book.imageURL,yourBookImage)
                                bookImageDownload.startDownload()
                                val tradeWithDownload = ImageDownloader(tradeWithBook.imageURL, requesterBookImage)
                                tradeWithDownload.startDownload()

                                yourBook.text = book.name
                                requesterBook.text = tradeWithBook.name

                                tradeAt.text = "At\n" + book.locate
                                tradeOn.text = "On\n"+request.date + "\n" + request.time
                            }
                        },{error ->
                            mCon.showToast(error.message)
                        })
                    }
                },{error ->
                    mCon.showToast(error.message)
                })
            }
        },{ error ->
            mCon.showToast(error.message)
        })
    }

    fun onClickAccept(view:View){
        update("accepted");
    }

    fun onClickReject(view: View){
        update("rejected")
    }

    fun update(status:String){
        if(requestData != null){
            val dataform = requestData!!.getDatabaseForm()
            val rCon = RequestController()
            requestData!!.status = status
            rCon.update(requestData!!)
            finish()
        }
    }
}


