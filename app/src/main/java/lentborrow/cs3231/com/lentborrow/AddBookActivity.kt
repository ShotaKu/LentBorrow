package lentborrow.cs3231.com.lentborrow

import android.app.Activity
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_book.*
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.controller.storage.FirebaseStorageController
import android.content.Intent
import android.provider.MediaStore
import lentborrow.cs3231.com.lentborrow.R.id.imageView
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import lentborrow.cs3231.com.lentborrow.R.id.image
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import java.io.IOException


class AddBookActivity : AppCompatActivity() {
    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var dataPath:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
    }


    fun onClickLoadImage(view: View){
        galleryIntent()
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data)
                dataPath = data.data;
            }
        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        cameraIcon_addBook.setImageBitmap(bm)
    }

    fun onClickPost(view: View){
        val bookNameTv = bookName_addBook
        val categorySpr = category_addBook
        val tradeAtTv = tradeAt_addBook
        val tradeTypeSpr = tradeType_addBook
        var lvCon = LocalValueController(this)
        val userID = lvCon.getID();

        val fsCon = FirebaseStorageController(userID,this)
        val bCon = BookController()
        //val

        if(dataPath != null){
            val progressBar = ProgressBar(this)
            //val uploader = null
            fsCon.setFile(dataPath!!,{ uploader ->
                Toast.makeText(this,"Upload Finish!!",Toast.LENGTH_LONG);
                val imageURL = uploader.downloadURL.toString();
                Log.d("Debug", imageURL)
//                var newBook = Book(categorySpr.selectedItem.toString()
//                ,imageURL,false,false,userID,tradeAtTv.text.toString()
//                ,bookNameTv.text.toString(),"N/A",tradeTypeSpr.selectedItem.toString())
                var newBook = Book("comic"
                        ,imageURL,false,false,userID,"test place"
                        ,"Test","N/A","for trading")
                newBook = bCon.create(newBook,userID);
            },{exeption ->
                Toast.makeText(this,"Upload Failed cause by " +exeption.message ,Toast.LENGTH_LONG);
            },{progress ->
                progressBar.progress = progress.toInt();
            })
        }
//
    }
}
