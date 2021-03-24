package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.message.message
import com.example.lab3.message.messageAdapter

class RankingActivity : AppCompatActivity() {
    private val TAG = "RankingActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_ranking)
        supportActionBar?.hide()

        val messageAdapter = messageAdapter(mutableListOf())
        val messageList = findViewById<RecyclerView>(R.id.messageList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)
        val myDB = DBHelper(this)
        val hashRanking = myDB.getBest10().toSortedMap(compareByDescending() {it})
        val result = hashRanking.toList().sortedBy { (_, value) -> value}.reversed().toMap()
        var c = 1;
        for (entry in  result){
            val new = message(entry.key, "Punkty: " + entry.value, c.toString())
            messageAdapter.addMessage(new)
            c++
        }

        val username = intent.getStringExtra("Username")
        val from = intent.getStringExtra("From")
        val backGame = findViewById<TextView>(R.id.backGame)
        val backLogin = findViewById<TextView>(R.id.backLogin)
        val backRegister = findViewById<TextView>(R.id.backRegister)

        if (from.equals("Game")){
            backGame.visibility = View.VISIBLE
            backGame.isEnabled = true
            backLogin.visibility = View.INVISIBLE
            backLogin.isEnabled = false
            backRegister.visibility = View.INVISIBLE
            backRegister.isEnabled = false
        } else if (from.equals("Login")){
            backGame.visibility = View.INVISIBLE
            backGame.isEnabled = false
            backLogin.visibility = View.VISIBLE
            backLogin.isEnabled = true
            backRegister.visibility = View.INVISIBLE
            backRegister.isEnabled = false
        } else if (from.equals("Register")){
            backGame.visibility = View.INVISIBLE
            backGame.isEnabled = false
            backLogin.visibility = View.INVISIBLE
            backLogin.isEnabled = false
            backRegister.visibility = View.VISIBLE
            backRegister.isEnabled = true
        }

        backGame.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
        }
        backLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        backRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }
}