package lentborrow.cs3231.com.lentborrow

import android.app.DownloadManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_request_box.*
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.Request
import lentborrow.cs3231.com.lentborrow.customCell.requestCell.RequestAdapter

class RequestBoxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_box)
        setTitle("Request Box");

        val userEmail = intent.getStringExtra("email");



        val rView = recyclerView
        rView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val student = ArrayList<Request>()
        //student.add(Request());
        //student.add()

        var adapter = RequestAdapter(student);
        rView.adapter = adapter;
    }
}
