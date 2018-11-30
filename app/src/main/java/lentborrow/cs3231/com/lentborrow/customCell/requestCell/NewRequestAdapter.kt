package lentborrow.cs3231.com.lentborrow.customCell.requestCell

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lentborrow.cs3231.com.lentborrow.R
import lentborrow.cs3231.com.lentborrow.controller.database.request.Request

class NewRequestAdapter(val requests: ArrayList<Request>) : RecyclerView.Adapter<NewRequestAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.request_customcell, parent, false)
        val newView = ViewHolder(v)
        return newView
    }

    override fun onBindViewHolder(viewHolder: NewRequestAdapter.ViewHolder, p1: Int) {

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

    }
}