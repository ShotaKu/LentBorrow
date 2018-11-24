package lentborrow.cs3231.com.lentborrow.generic

import android.content.Context
import android.widget.Toast

class MessageController(context: Context){
    val context:Context = context

    fun showToast(message:String){
        Toast.makeText(this.context,message,Toast.LENGTH_LONG);
    }
}