package lentborrow.cs3231.com.lentborrow.generic

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v4.print.PrintHelper
import android.util.Log
import android.widget.ImageView

class ImageDownloader(imageURL:String, imageView:ImageView){

    var imageURL:String = ""
    var imageView:ImageView? = null

    init {
        this.imageURL = imageURL
        this.imageView = imageView
    }

    fun startDownload(finishCallback:(() ->Unit)? = null){
        downloadImageTask(imageView!!,finishCallback).execute(imageURL)
    }

    private class downloadImageTask(internal var bmImage: ImageView, val finishCallback:(() ->Unit)?)
        : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urldisplay = urls[0]
            var mIcon11: Bitmap? = null
            try {
                val `in` = java.net.URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
            }
            return mIcon11
        }

        override fun onPostExecute(result: Bitmap) {
            bmImage.setImageBitmap(result)
            if(finishCallback != null)
                run {finishCallback!!()}
        }
    }

}