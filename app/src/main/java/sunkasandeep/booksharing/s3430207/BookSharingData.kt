package sunkasandeep.booksharing.s3430207

import android.content.Context

object BookSharingData {

    fun saveBookHolderStatus(context: Context, value: Boolean) {
        val userLogin = context.getSharedPreferences("BOOK_HOLDER_DATA", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putBoolean("BH_STATUS", value).apply()
    }

    fun getBookHolderStatus(context: Context): Boolean {
        val userLogin = context.getSharedPreferences("BOOK_HOLDER_DATA", Context.MODE_PRIVATE)
        return userLogin.getBoolean("BH_STATUS", false)
    }

    fun saveBookHolderName(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("BOOK_HOLDER_DATA", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("BH_NAME", value).apply()
    }

    fun getBookHolderName(context: Context): String {
        val userLogin = context.getSharedPreferences("BOOK_HOLDER_DATA", Context.MODE_PRIVATE)
        return userLogin.getString("BH_NAME", "")!!
    }

    fun saveBookHolderMail(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("BOOK_HOLDER_DATA", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("BH_MAIL", value).apply()
    }

    fun getBookHolderMail(context: Context): String {
        val userLogin = context.getSharedPreferences("BOOK_HOLDER_DATA", Context.MODE_PRIVATE)
        return userLogin.getString("BH_MAIL", "")!!
    }


}