package lentborrow.cs3231.com.lentborrow.controller.storage

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.content.ContentResolver
import java.util.*


class FirebaseStorageController(userID:String,context: Context) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.getReference("/Test")
    var userID = "";
    var context = context;

    init {
        this.userID = userID;
        this.context = context;
    }

    fun setFile(data:Uri, successCallback: (uploader:FirebaseStorageUploader) -> Unit
                , failedCallback: (exeption: Exception) -> Unit
                , progressCallback:(progress:Double)->Unit ){
        val imageRef = storage.getReference(userID+"/"+getRandomFileName()+getFileType(data))
        storageRef.putFile(data)
                .addOnSuccessListener { taskSnapshot ->
                    //@TODO: test
                    storageRef.downloadUrl.addOnSuccessListener{ uri ->
                        val uploader = FirebaseStorageUploader(data,context)
                        uploader.setUploadFinish(storageRef.path,storageRef,uri)
                        successCallback(uploader)
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle unsuccessful uploads
                    failedCallback(exception)
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()
                    progressCallback(progress);
                }
    }

    fun setImage(data: ByteArray, successCallback: (downLoadURL: String) -> Unit
                 , failedCallback: (exeption: Exception) -> Unit) {
        storageRef.putBytes(data)
                .addOnSuccessListener { taskSnapshot ->
                    //@TODO: test
                    successCallback(taskSnapshot.uploadSessionUri.toString())
                }
                .addOnFailureListener { exception ->
                    // Handle unsuccessful uploads
                    failedCallback(exception)
                }
    }

    fun getFileType(uri: Uri): String? {
        val cR = context.getContentResolver()
        val mime = MimeTypeMap.getSingleton()
        val type = mime.getExtensionFromMimeType(cR.getType(uri))
        return type;
    }

    fun getRandomFileName():String{
        return UUID.randomUUID().toString();
    }
}