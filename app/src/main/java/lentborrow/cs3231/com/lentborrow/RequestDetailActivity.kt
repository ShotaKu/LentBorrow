package lentborrow.cs3231.com.lentborrow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_request_detail.*
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.generic.ImageDownloader
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class RequestDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)
        title = "RequestDetail"
        val mCon = MessageController(this)
        val requestid = intent.getStringExtra("requestID")

        val rCon = RequestController()

        val lvCon = LocalValueController(this)
        val bCon = BookController()

        rCon.getRequestByRequestID(requestid, { request ->

            if (request != null) {
                bCon.getBookByID(request.bookID, { book: Book? ->
                    if (book != null) {
                        bCon.getBookByID(request.tradeWithID, { Trade: Book? ->
                            if (Trade != null) {
                                val bookimage = ImageDownloader(book!!.imageURL, yourBookImage)
                                bookimage.startDownload()
                                val requestimage = ImageDownloader(Trade!!.imageURL, requesterBookImage)
                                requestimage.startDownload()
                                requesterBook.text = Trade.name.toString()
                                yourBook.text = book.name
                                tradeAt.text =  "At"+"\n"+book.locate
                                tradeOn.text = "On"+"\n"+request.time+"\n"+request.date

                            }


                        }, { error: DatabaseError ->

                        })
                    }

                }, { error: DatabaseError ->

                })

            }
        }, { error ->
            mCon.showToast("Error happen when get request.")
        })
    }
}


