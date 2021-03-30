package com.example.lab5

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.message.commMessage
import com.example.lab5.message.commMessageAdapter
import com.example.lab5.message.postMessage
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommentsActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        window.statusBarColor = ContextCompat.getColor(this, R.color.mydark)
        supportActionBar?.hide()

        val messageAdapter = commMessageAdapter(mutableListOf())

        val messageList = findViewById<RecyclerView>(R.id.commsList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)

        val userId = intent.getStringExtra("userId")
        val postId = intent.getStringExtra("postId")
        val mydb = DBHelper(this)
        val postComms = postId?.let { mydb.getPostComms(it) }
        val postCursor = postId?.let { mydb.getPost(it) }
        postCursor!!.moveToFirst()

        val postsOwner = findViewById<TextView>(R.id.postsOwnerComms)
        val postTitle = findViewById<TextView>(R.id.postTitleComms)
        val postBody = findViewById<TextView>(R.id.postBodyComms)
        postsOwner.text = userId?.let { mydb.getUserName(it) }
        postTitle.text = postCursor.getString(postCursor.getColumnIndex("title"))
        postBody.text = postCursor.getString(postCursor.getColumnIndex("body"))

        try {
            while (postComms?.moveToNext()!!) {
                val name = postComms.getString(postComms.getColumnIndex("name"))
                val mail = postComms.getString(postComms.getColumnIndex("mail"))
                val body = postComms.getString(postComms.getColumnIndex("body"))

                val new = commMessage(name, mail, body)
                messageAdapter.addMessage(new)
            }
        } finally {
            if (postComms != null && !postComms.isClosed) {
                postComms.close()
                mydb.close()
            }
        }

        val floatingMenu = findViewById<FloatingActionButton>(R.id.comms_floating_action_button)
        val usersMenu = findViewById<ExtendedFloatingActionButton>(R.id.comms_floating_user_list)
        val postsMenu = findViewById<ExtendedFloatingActionButton>(R.id.floating_posts_comms)

        usersMenu.isVisible = false
        usersMenu.isEnabled = false
        postsMenu.isVisible = false
        postsMenu.isEnabled = false

        floatingMenu.setOnClickListener {
            if (!usersMenu.isVisible) {
                usersMenu.isVisible = true
                usersMenu.isEnabled = true
                postsMenu.isVisible = true
                postsMenu.isEnabled = true
            } else {
                usersMenu.isVisible = false
                usersMenu.isEnabled = false
                postsMenu.isVisible = false
                postsMenu.isEnabled = false
            }
        }

        usersMenu.setOnClickListener {
            val intent = Intent(applicationContext, UsersActivity::class.java)
            startActivity(intent)
        }

        postsMenu.setOnClickListener {
            val intent = Intent(applicationContext, PostsActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}