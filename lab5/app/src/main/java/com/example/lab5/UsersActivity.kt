package com.example.lab5

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.message.postMessage
import com.example.lab5.message.userMessage
import com.example.lab5.message.userMessageAdapter

class UsersActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        window.statusBarColor = ContextCompat.getColor(this, R.color.mydark)
        supportActionBar?.hide()

        val messageAdapter = userMessageAdapter(mutableListOf())

        val messageList = findViewById<RecyclerView>(R.id.userList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)

        val mydb = DBHelper(this)
        val allUsers = mydb.getUsers()

        if (allUsers != null) {
            while (allUsers.moveToNext()) {
                val name = allUsers.getString(allUsers.getColumnIndex("name"))
                val mail = allUsers.getString(allUsers.getColumnIndex("mail"))
                val splName = name.split(" ")
                val initial = splName[0].first().plus(splName[1].first().toString())

                val todoCount = mydb.getTodoCount(allUsers.getInt(allUsers.getColumnIndex("id")))
                val postCount = mydb.getPostCount(allUsers.getInt(allUsers.getColumnIndex("id")))

                val new = userMessage(initial, name, mail, "Todos: $todoCount", "Posts: $postCount")
                messageAdapter.addMessage(new)
            }
        }

        messageList.addOnItemTouchListener(
            RecyclerItemClickListenr(
                this,
                messageList,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(applicationContext, PostsActivity::class.java)
                        intent.putExtra("userId", (position+1).toString())
                        startActivity(intent)
                    }
                })
        )
    }
}
