package com.example.lab5

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.message.message
import com.example.lab5.message.messageAdapter

class UsersActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        window.statusBarColor = ContextCompat.getColor(this, R.color.mydark)
        supportActionBar?.hide()

        val messageAdapter = messageAdapter(mutableListOf())

        val messageList = findViewById<RecyclerView>(R.id.userList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)

        val mydb = DBHelper(this)

        val allUsers = mydb.getUsers()

        if (allUsers != null) {
            while(allUsers.moveToNext()) {
                val name = allUsers.getString(allUsers.getColumnIndex("name"))
                val mail = allUsers.getString(allUsers.getColumnIndex("mail"))
                val splName = name.split(" ")
                val initial = splName[0].first().plus(splName[1].first().toString())

                val new = message(initial, name, mail, "1", "1")
                messageAdapter.addMessage(new)
            }
        }
    }
}