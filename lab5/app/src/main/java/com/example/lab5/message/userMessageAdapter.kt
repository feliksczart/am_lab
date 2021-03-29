package com.example.lab5.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.R


class userMessageAdapter(
    private val messages: MutableList<userMessage>
) : RecyclerView.Adapter<userMessageAdapter.messageViewHolder>() {
    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val initial = itemView.findViewById<TextView>(R.id.initial)
        val name = itemView.findViewById<TextView>(R.id.name)
        val mail = itemView.findViewById<TextView>(R.id.mail)
        val tasks = itemView.findViewById<TextView>(R.id.tasks)
        val posts = itemView.findViewById<TextView>(R.id.posts)

        fun bind(curMessage: userMessage){
            initial.text = curMessage.initial
            name.text = curMessage.name
            mail.text = curMessage.mail
            tasks.text = curMessage.tasks
            posts.text = curMessage.posts
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    fun addMessage(userMessage: userMessage) {
        messages.add(userMessage)
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