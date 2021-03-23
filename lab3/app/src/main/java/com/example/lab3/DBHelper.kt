package com.example.lab3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.lab3.message.messageAdapter
import java.util.*
import kotlin.collections.HashMap

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
        contentValues.put("username", username.toLowerCase(Locale.ROOT))
        contentValues.put("password", password.toLowerCase(Locale.ROOT))
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

    fun setBestPts(username: String, bestpts: Number, applicationContext: Context) {
        val myDBwritable = this.writableDatabase
        val myDBreadable = this.readableDatabase

        val cursor = myDBreadable.rawQuery("select bestpts from users where username = $username;", null)
        try {
            while (cursor.moveToNext()) {
                val prevBest = cursor.getInt(cursor.getColumnIndex("bestpts"))
                if (prevBest < bestpts.toInt()){
                    myDBwritable!!.execSQL("update users set bestpts = $bestpts where username = $username;")
                }
            }
        } finally {
            cursor.close()
        }
    }

    fun getBest10(): HashMap<String, Int> {
        val myDBreadable = this.readableDatabase
        val hashRanking = HashMap<String, Int>()
        val cursor = myDBreadable.rawQuery("select username, bestpts from users order by bestpts desc limit 10;", null)

        try {
            while (cursor.moveToNext()) {
                val user = cursor.getInt(cursor.getColumnIndex("username")).toString()
                val scor = cursor.getInt(cursor.getColumnIndex("bestpts"))
                hashRanking[user] = scor
            }
        } finally {
            cursor.close()
        }

        return hashRanking
    }
}