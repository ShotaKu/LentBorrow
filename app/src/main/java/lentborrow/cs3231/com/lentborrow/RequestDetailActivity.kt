package lentborrow.cs3231.com.lentborrow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_request_box.*
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.NewRequestAdapter
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class RequestDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_detail)
        title = "Request Detail"
        val mCon = MessageController(this)

        val rCon = RequestController()
        val lvCon = LocalValueController(this)

        rCon.getRequestsByOwnerID(lvCon.getID(), { requests ->
            val filtered = rCon.filterByWaitingRequests(requests)
            if (rCon.filterByRejectedRequests(requests) == filtered) {
                mCon.showToast("Rejected")

            } else if (rCon.filterByAcceptedRequests(requests) == filtered) {
                mCon.showToast("Accepted")
                val rView = newReqestList
                rView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                var adapter = NewRequestAdapter(filtered)
                rView.adapter = adapter

            }
        }, { error ->
            mCon.showToast("Error happen when get request.")
        })
    }
}


