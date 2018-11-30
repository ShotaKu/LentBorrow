package lentborrow.cs3231.com.lentborrow.controller.activity

import android.content.Context

class ActivityMigrationController() : ActivityMigrationBuilder(){

    private constructor(context: Context,activityName: String):this(){
        super.set(context,activityName)
    }

    fun setLoginActivity(context: Context): ActivityMigrationBuilder {
        return ActivityMigrationController(context,"Login")
    }

    fun setRegistrationActivity(context: Context): ActivityMigrationBuilder {
        return ActivityMigrationController(context,"Register")
    }

    fun setRequestBoxActivity(context: Context): ActivityMigrationBuilder {
        return ActivityMigrationController(context,"RequestBox")
    }

    fun setSearchActivity(context: Context):ActivityMigrationBuilder{
        return ActivityMigrationController(context,"Search")
    }

    fun setBookDetail(context: Context):ActivityMigrationBuilder {
        return ActivityMigrationController(context,"BookDetail")
    }

    fun setUserDetail(context: Context):ActivityMigrationBuilder {
        return ActivityMigrationController(context,"UserDetail")
    }

    fun setAddBook(context: Context):ActivityMigrationBuilder {
        return ActivityMigrationController(context,"AddBook")
    }

    fun setUserBook(context: Context):ActivityMigrationBuilder {
        return ActivityMigrationController(context,"UserBook")
    }

    fun setEditBook(context: Context):ActivityMigrationController{
        return ActivityMigrationController(context,"EditBook")
    }

    fun setRequestLog(context: Context):ActivityMigrationController{
        return ActivityMigrationController(context,"RequestLog")
    }

//    fun back(context: Context){
//        context.
//    }
}