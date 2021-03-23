package com.example.lab3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.lab3.message.messageAdapter

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        private const val DATABASE_VER = 2
        private const val DATABASE_NAME = "Login.db"
    }

    override fun onCreate(myDB: SQLiteDatabase?) {
        myDB!!.execSQL("create table users(username text primary key, password text, currpts number, bestpts number);")
    }

    override fun onUpgrade(myDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        myDB!!.execSQL("drop table if exists users")
        onCreate(myDB!!)
    }

    fun insertData(username: String, password: String): Boolean {
        val myDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        contentValues.put("currpts", 0)
        contentValues.put("bestpts", 0)
        val result = myDB.insert("users", null, contentValues)

        return !result.equals(-1)
    }

    fun checkUserName(username: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from users where username = ?", arrayOf(username))

        return cursor.count > 0
    }

    fun checkUserPassword(username: String, password: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery(
            "select * from users where username = ? and password = ?",
            arrayOf(username, password)
        )

        return cursor.count > 0
    }

    fun setCurrPts(username: String, currpts: Number) {
        val myDB = this.writableDatabase
        myDB!!.execSQL("update users set currpts = $currpts where username = $username;")
    }
}