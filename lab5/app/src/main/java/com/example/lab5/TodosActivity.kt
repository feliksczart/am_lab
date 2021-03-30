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
import com.example.lab5.message.postMessage
import com.example.lab5.message.todoMessage
import com.example.lab5.message.todoMessageAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodosActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos)
        window.statusBarColor = ContextCompat.getColor(this, R.color.mydark)
        supportActionBar?.hide()

        val messageAdapter = todoMessageAdapter(mutableListOf())

        val messageList = findViewById<RecyclerView>(R.id.todoList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)


        val userId = intent.getStringExtra("userId")
        val mydb = DBHelper(this)
        val userTodos = userId?.let { mydb.getUserTodos(it) }

        val todosOwner = findViewById<TextView>(R.id.todoOwner)
        todosOwner.text = userId?.let { mydb.getUserName(it) }

        val todoStatus = findViewById<TextView>(R.id.todoStatus)

        try {
            while (userTodos?.moveToNext()!!) {
                val status = userTodos.getString(userTodos.getColumnIndex("completed"))
                val title = userTodos.getString(userTodos.getColumnIndex("title"))

                val new = todoMessage(status, title)
                messageAdapter.addMessage(new)
            }
        } finally {
            if (userTodos != null && !userTodos.isClosed) {
                userTodos.close()
                mydb.close()
            }
        }

        val floatingMenu = findViewById<FloatingActionButton>(R.id.todo_floating_action_button)
        val usersMenu = findViewById<ExtendedFloatingActionButton>(R.id.todo_floating_user_list)
        val postsMenu = findViewById<ExtendedFloatingActionButton>(R.id.todo_floating_posts)

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