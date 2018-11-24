package lentborrow.cs3231.com.lentborrow.controller.storage

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.StorageReference
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController

class FirebaseStorageUploader(path:Uri,context:Context){

    var path:Uri? = null;
    var onStoragePath:String = "";
    var uploadReference:StorageReference? = null
    var downloadURL:Uri? = null;
    var fsCon:FirebaseStorageController? = null;

    init {
        this.path = path;
        val lvCon = LocalValueController(context)
        this.fsCon = FirebaseStorageController(lvCon.getID(),context)
    }

    fun setUploadFinish(uploadPath:String, uploadReference: StorageReference,downloadUri:Uri){
        this.onStoragePath = uploadPath
        this.uploadReference = uploadReference
        this.downloadURL = downloadUri
    }

    fun cancelUpload(){
        uploadReference!!.delete()
    }
}