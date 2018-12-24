package lentborrow.cs3231.com.lentborrow

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.activity_request_box.*
import kotlinx.android.synthetic.main.activity_request_detail.*
import lentborrow.cs3231.com.lentborrow.R.id.tradeOn
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.request.Request
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.NewRequestAdapter
import lentborrow.cs3231.com.lentborrow.generic.ImageDownloader
import lentborrow.cs3231.com.lentborrow.generic.MessageController
import java.util.*

class RequestDetailActivity : AppCompatActivity() {

    var bCon = BookController()
    val rCon = RequestController()
    val mCon = MessageController(this)
    val lvCon = LocalValueController(this)
    var showedBook: Book? = null
    var userBooks: ArrayList<Book> = arrayListOf()
    val cal = Calendar.getInstance()
    val RESULT_FROM_DATEPICKER = 1
    val RESULT_FROM_TIMEPICKER = 2
    var amController = ActivityMigrationController()
    var bookID = ""
    var tradeId  = ""
    var requests = ArrayList<Request>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)


        val text = tradeOn
        val yea = cal.get(Calendar.YEAR)
        val mon = cal.get(Calendar.MONTH)
        val date = cal.get(Calendar.DAY_OF_MONTH)
        val h = cal.get(Calendar.HOUR)
        val m = cal.get(Calendar.MINUTE)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            text.text = year.toString() + "-" + monthOfYear + "-" + dayOfMonth

        }, yea, mon, date)
        dpd.show()
        val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hour, min ->
            text.text = hour.toString() + ":" + min.toString()
        }, h, m, false)
        tpd.show()



        getBook(bookID)
        getBook(tradeId)


        refuseRequest.setOnClickListener {
            rCon.filterByRejectedRequests(requests)



        }
        acceptRequest.setOnClickListener {
            rCon.filterByAcceptedRequests(requests)


//            rCon.getRequestsByRequesterID(lvCon.getID(), { requests ->
//                val filtered = rCon.filterByWaitingRequests(requests)
//                yourBook.text = filtered.toString()
////                val rView = newReqestList
////                rView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
////                var adapter = NewRequestAdapter(filtered)
////                rView.adapter = adapter
//            }, { error ->
//                mCon.showToast("Error happen when get request.")
//            })
        }
    }
            fun getBook(bookID: String) {

                bCon.getBookByID(bookID, { book ->
                    if (book != null) {
                        showedBook = book
                        showBookDetail(book)
                        val lvCon = LocalValueController(this)

                        var isUsers = lvCon.getID() == book.lentBy

                        if (!isUsers) {
                            rCon.getRequestsByBookID(book.id, { requests ->
                                var isRequested = false;
                                for (request in requests) {
                                    if (request.requesterID == lvCon.getID()) {
                                        if (request.status == "wait for check") {
                                            isRequested = true;
                                            break
                                        }
                                    }
                                }

                            }, { error ->
                                MessageController(this).showToast("Error when get request information")
                            })
                        } else {

                        }
                    } else
                        MessageController(this).showToast("Book detail broken")
                }, { error ->
                    MessageController(this).showToast("Book not found")
                })
            }

            fun showBookDetail(book: Book) {


                yourBook.text = book.name


                tradeAt.text = "Trade at " + book.locate




                val iDown = ImageDownloader(book.imageURL,yourBookImage)
                iDown.startDownload()
            }
        }










//
//        title = "RequestDetail"
//        val mCon = MessageController(this)
//
//        val rCon = RequestController()
//        val lvCon = LocalValueController(this)
//
//        rCon.getRequestsByOwnerID(lvCon.getID(), { requests ->
//            val filtered = rCon.filterByWaitingRequests(requests)
//            if (rCon.filterByRejectedRequests(requests) == filtered) {
//                mCon.showToast("Rejected")
//
//            } else if (rCon.filterByAcceptedRequests(requests) == filtered) {
//                mCon.showToast("Accepted")
//                val rView = newReqestList
//                rView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//                var adapter = NewRequestAdapter(filtered)
//                rView.adapter = adapter
//
//            }
//        }, { error ->
//            mCon.showToast("Error happen when get request.")
//        })
//    }
//}
//

