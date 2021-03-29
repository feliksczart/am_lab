package com.example.lab5

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.message.postMessage
import com.example.lab5.message.postMessageAdapter
import com.example.lab5.message.userMessage
import com.example.lab5.message.userMessageAdapter

class PostsActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        window.statusBarColor = ContextCompat.getColor(this, R.color.mydark)
        supportActionBar?.hide()

        val messageAdapter = postMessageAdapter(mutableListOf())

        val postsOwner = findViewById<TextView>(R.id.postsOwner)
        val messageList = findViewById<RecyclerView>(R.id.postList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)

        val userId = intent.getStringExtra("userId")
        val mydb = DBHelper(this)
        val userPosts = userId?.let { mydb.getUserPosts(it) }

        postsOwner.text = userId?.let { mydb.getUserName(it) }

        if (userPosts != null) {
            while (userPosts.moveToNext()) {
                val title = userPosts.getString(userPosts.getColumnIndex("title"))
                val body = userPosts.getString(userPosts.getColumnIndex("body"))

                val new = postMessage(title, body)
                messageAdapter.addMessage(new)
            }
        }
    }
}