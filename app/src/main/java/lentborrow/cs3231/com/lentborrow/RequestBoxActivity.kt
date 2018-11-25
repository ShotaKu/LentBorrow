package lentborrow.cs3231.com.lentborrow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_request_box.*
import lentborrow.cs3231.com.lentborrow.controller.database.request.RequestController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.RequestAdapter

class RequestBoxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_box)
        setTitle("Request Box");


        val rCon = RequestController()
        val lvCon = LocalValueController(this)
        rCon.getRequestsByOwnerID(lvCon.getID(), { requests ->
            val rView = recyclerView
            rView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            var adapter = RequestAdapter(requests);
            rView.adapter = adapter;
        }, { error ->

        })
    }
}
