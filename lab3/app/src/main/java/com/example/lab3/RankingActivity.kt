package com.example.lab3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.message.message
import com.example.lab3.message.messageAdapter

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        supportActionBar?.hide()

        val messageAdapter = messageAdapter(mutableListOf())
        val messageList = findViewById<RecyclerView>(R.id.messageList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)
        val myDB = DBHelper(this)

        val hashRanking = myDB.getBest10()

        for (key in  hashRanking.keys){
            val new = message("$key", "Punkty: " + hashRanking[key].toString())
            messageAdapter.addMessage(new)
        }
    }
}