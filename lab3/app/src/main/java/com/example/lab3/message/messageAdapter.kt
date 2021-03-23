package com.example.lab3.message

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.R


class messageAdapter(
    private val messages: MutableList<message>
) : RecyclerView.Adapter<messageAdapter.messageViewHolder>() {
    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rankingUser = itemView.findViewById<TextView>(R.id.rankingUser)
        val rankingScore = itemView.findViewById<TextView>(R.id.rankingScore)
        val positionText = itemView.findViewById<TextView>(R.id.positionText)

        fun bind(curMessage: message){
            rankingUser.text = curMessage.rankingUser
            rankingScore.text = curMessage.rankingScore
            positionText.text = curMessage.positionText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ranking_item, parent, false)
        )
    }

    fun addMessage(message: message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curMessage = messages[position]
        holder.bind(curMessage)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}