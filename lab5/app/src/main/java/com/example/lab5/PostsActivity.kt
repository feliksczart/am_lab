package com.example.lab5

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.message.postMessage
import com.example.lab5.message.postMessageAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton


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

        try {
            while (userPosts?.moveToNext()!!) {
                val title = userPosts.getString(userPosts.getColumnIndex("title"))
                val body = userPosts.getString(userPosts.getColumnIndex("body"))

                val new = postMessage(title, body)
                messageAdapter.addMessage(new)
            }
        } finally {
            if (userPosts != null && !userPosts.isClosed) {
                userPosts.close()
                mydb.close()
            }
        }

        val floatingMenu = findViewById<FloatingActionButton>(R.id.floating_action_button)
        val usersMenu = findViewById<ExtendedFloatingActionButton>(R.id.floating_user_list)
        val todosMenu = findViewById<ExtendedFloatingActionButton>(R.id.floating_todos)

        usersMenu.isVisible = false
        usersMenu.isEnabled = false
        todosMenu.isVisible = false
        todosMenu.isEnabled = false

        floatingMenu.setOnClickListener {
            if (!usersMenu.isVisible) {
                usersMenu.isVisible = true
                usersMenu.isEnabled = true
                todosMenu.isVisible = true
                todosMenu.isEnabled = true
            } else {
                usersMenu.isVisible = false
                usersMenu.isEnabled = false
                todosMenu.isVisible = false
                todosMenu.isEnabled = false
            }
        }

        usersMenu.setOnClickListener {
            val intent = Intent(applicationContext, UsersActivity::class.java)
            startActivity(intent)
        }
    }
}