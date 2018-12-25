package lentborrow.cs3231.com.lentborrow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_request_box.*
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.NewRequestAdapter
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.RequestAdapter
import lentborrow.cs3231.com.lentborrow.generic.MessageController


class RequestBoxActivity : AppCompatActivity() {

    //@ToDo MEMO: Use this activity as Request Log Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_box)
        title = "Request Log"
        val mCon = MessageController(this)

        val rCon = RequestController()
        val lvCon = LocalValueController(this)

        rCon.getRequestsByOwnerID(lvCon.getID(), { requests ->
            val filtered = rCon.filterByWaitingRequests(requests)
            val rView = newReqestList
            rView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            var adapter = NewRequestAdapter(filtered)
            rView.adapter = adapter
        }, { error ->
            mCon.showToast("Error happen when get request.")
        })
    }
}
