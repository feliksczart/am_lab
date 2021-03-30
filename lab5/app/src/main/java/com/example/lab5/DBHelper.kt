package com.example.lab5

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
        myDB!!.execSQL("create table posts(id number primary key, userId number, title text, body text, foreign key(userId) references users(id));")
        myDB!!.execSQL("create table comments(id number primary key, postId number, name text, mail text, body text, foreign key(postId) references posts(id));")
    }

    override fun onUpgrade(myDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        myDB!!.execSQL("drop table if exists users")
        myDB!!.execSQL("drop table if exists todos")
        myDB!!.execSQL("drop table if exists posts")
        myDB!!.execSQL("drop table if exists comments")
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
        contentValues.put("id", array[0])
        contentValues.put("userId", array[1])
        contentValues.put("title", array[2])
        contentValues.put("completed", array[3])
        myDB.insert("todos", null, contentValues)
    }

    fun insertPost(array: ArrayList<String>) {
        val myDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", array[0])
        contentValues.put("userId", array[1])
        contentValues.put("title", array[2])
        contentValues.put("body", array[3])
        myDB.insert("posts", null, contentValues)
    }

    fun insertComment(array: ArrayList<String>) {
        val myDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", array[0])
        contentValues.put("postId", array[1])
        contentValues.put("name", array[2])
        contentValues.put("mail", array[3])
        contentValues.put("body", array[4])
        myDB.insert("comments", null, contentValues)
    }

    fun checkUser(id: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from users where id = ?", arrayOf(id))

        return cursor.count == 0.also { cursor.close() }
    }

    fun checkTodo(id: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from todos where id = ?", arrayOf(id))

        return cursor.count == 0.also { cursor.close() }
    }

    fun checkPost(id: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from posts where id = ?", arrayOf(id))

        return cursor.count == 0.also { cursor.close() }
    }

    fun checkComment(id: String): Boolean {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from comments where id = ?", arrayOf(id))

        return cursor.count == 0.also { cursor.close() }
    }

    fun getUsers(): Cursor? {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from users", null)

        return cursor
    }

    fun getTodos(): Cursor? {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from todos", null)

        return cursor
    }

    fun getUserPosts(userId: String): Cursor? {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from posts where userId = ?", arrayOf(userId))

        return cursor
    }

    fun getUserTodos(userId: String): Cursor? {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select * from todos where userId = ?", arrayOf(userId))

        return cursor
    }

    fun getTodoCount(userId: Number): Int {
        val myDB = this.readableDatabase
        val cursor = myDB.rawQuery(
            "select * from todos where userId = ? and completed = 'false'",
            arrayOf(userId.toString())
        )

        return cursor.count.also { cursor.close() }
    }

    fun getPostCount(userId: Number): Int {
        val myDB = this.readableDatabase
        val cursor =
            myDB.rawQuery("select * from posts where userId = ?", arrayOf(userId.toString()))

        return cursor.count.also { cursor.close() }
    }

    fun getUserName(userId: String): String? {
        val myDB = this.writableDatabase
        val cursor = myDB.rawQuery("select name from users where id = ?", arrayOf(userId))
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("name")).also { cursor.close() }
    }

    fun getPostComms(postId: String): Cursor? {
        val myDB = this.readableDatabase
        val cursor = myDB.rawQuery("select * from comments where postId = ?", arrayOf(postId))
        Log.i("Siema",cursor.toString())
        return cursor
    }

    fun getPost(postId: String): Cursor? {
        val myDB = this.readableDatabase
        val cursor =
            myDB.rawQuery("select * from posts where id = ?", arrayOf(postId))

        return cursor
    }
}