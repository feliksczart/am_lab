package com.example.lab3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val hashRanking = myDB.getBest10().toSortedMap(compareByDescending() {it})
        val result = hashRanking.toList().sortedBy { (_, value) -> value}.reversed().toMap()

        for (entry in  result){
            val new = message(entry.key, "Punkty: " + entry.value)
            messageAdapter.addMessage(new)
        }
    }
}