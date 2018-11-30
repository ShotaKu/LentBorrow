package lentborrow.cs3231.com.lentborrow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_request_box.*
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.RequestAdapter
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class RequestLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_log)

        title = "Request Log"
        val mCon = MessageController(this)

        val rCon = RequestController()
        val lvCon = LocalValueController(this)
        val status = intent.getStringExtra("status")

        rCon.getRequestsByOwnerID(lvCon.getID(), { requests ->
            var filtered = requests

            if (0 < filtered.count()) {
                val rView = recyclerView
                rView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                var adapter = RequestAdapter(filtered)
                rView.adapter = adapter
            } else {
                mCon.showToast("No requests")
            }
        }, { error ->
            mCon.showToast("Error happen when get request.")
        })
    }
}
