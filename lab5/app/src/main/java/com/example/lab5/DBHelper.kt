package com.example.lab5

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        private const val DATABASE_VER = 2
        private const val DATABASE_NAME = "Users.db"
    }

    override fun onCreate(myDB: SQLiteDatabase?) {
        myDB!!.execSQL("create table users(id number primary key, name text, mail text);")
        myDB!!.execSQL("create table todos(id number primary key, userId number, title text, completed text, foreign key(userId) references users(id));")
    }

    override fun onUpgrade(myDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        myDB!!.execSQL("drop table if exists users")
        myDB!!.execSQL("drop table if exists todos")
        onCreate(myDB!!)
    }

    fun insertUser(array: ArrayList<String>) {
        val myDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", array[0])
        contentValues.put("name", array[1])
        contentValues.put("mail", array[2])
        myDB.insert("users", null, contentValues)
    }

    fun insertTodo(array: ArrayList<String>) {
        val myDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", array[1])
        contentValues.put("userId", array[0])
        contentValues.put("title", array[1])
        contentValues.put("completed", array[2])
        myDB.insert("todos", null, contentValues)
    }

    fun checkUser(id: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from users where id = ?", arrayOf(id))

        return cursor.count == 0
    }

    fun checkTodo(id: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from todos where id = ?", arrayOf(id))

        return cursor.count == 0
    }

    fun getUsers(): Cursor? {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from users",null)

        return cursor
    }

    fun getTodos(): Cursor? {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from todos",null)

        return cursor
    }

    fun getTodoCount(userId: Number): Int {
        val myDB = this.readableDatabase
        val cursor = myDB.rawQuery("select * from todos where userId = ? and completed = 'false'", arrayOf(userId.toString()))

        return cursor.count
    }
}