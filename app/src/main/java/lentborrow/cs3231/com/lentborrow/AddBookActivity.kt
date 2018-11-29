package lentborrow.cs3231.com.lentborrow

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_book.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.controller.storage.FirebaseStorageController
import lentborrow.cs3231.com.lentborrow.generic.MessageController
import java.io.IOException


class AddBookActivity : AppCompatActivity() {
    //private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var dataPath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
    }


    fun onClickLoadImage(view: View) {
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
            if (requestCode == SELECT_FILE) {
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
                cameraIcon_addBook.setImageBitmap(bm)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            MessageController(this).showToast("Please select book image!")
        }
    }

    fun onClickPost(view: View) {
        val bookNameTv = bookName_addBook.text.toString()
        val categorySpr = category_addBook.selectedItem.toString()
        val tradeAtTv = tradeAt_addBook.text.toString()
        val tradeTypeSpr = tradeType_addBook.selectedItem.toString()
        var lvCon = LocalValueController(this)
        val userID = lvCon.getID();

        val fsCon = FirebaseStorageController(userID, this)
        val bCon = BookController()

        if (dataPath != null) {
            if (!bookNameTv.isEmpty()) {
                if (categorySpr != resources.getStringArray(R.array.categoryList)[0]) {
                    if (!tradeAtTv.isEmpty()) {
                        if (tradeTypeSpr != resources.getStringArray(R.array.tradeTypeList)[0]) {
                            val progressBar = progressBar
                            val bl = blocker
                            progressBar.visibility = View.VISIBLE
                            bl.visibility = View.VISIBLE
                            //val uploader = null
                            fsCon.setFile(dataPath!!, { uploader ->
                                Toast.makeText(this, "Upload Finish!!", Toast.LENGTH_LONG);
                                val imageURL = uploader.downloadURL.toString();
                                Log.d("Debug", imageURL)
                                var newBook = Book(categorySpr
                                        , imageURL, false, false, userID, tradeAtTv
                                        , bookNameTv, "N/A", tradeTypeSpr)
                                newBook = bCon.create(newBook, userID);
                                finish()
                                //ActivityMigrationController().setUserBook(this).go()
                            }, { exeption ->
                                Toast.makeText(this, "Upload Failed cause by " + exeption.message, Toast.LENGTH_LONG);
                            }, { progress ->
                                progressBar.progress = progress.toInt();
                            })
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please select book image!", Toast.LENGTH_LONG);
        }
//
    }
}
