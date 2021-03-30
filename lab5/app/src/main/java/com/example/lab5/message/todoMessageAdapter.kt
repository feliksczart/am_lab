package com.example.lab5.message

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.R

class todoMessageAdapter(
    private val messages: MutableList<todoMessage>
) : RecyclerView.Adapter<todoMessageAdapter.messageViewHolder>() {

    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val status = itemView.findViewById<TextView>(R.id.todoStatus)
        val title = itemView.findViewById<TextView>(R.id.todoTitle)

        fun bind(curMessage: todoMessage){

            if (curMessage.status == "false"){
                status.setTextColor(Color.parseColor("#b90e0a"))
                status.text = "TODO"
            } else{
                status.setTextColor(Color.parseColor("#149414"))
                status.text = "DONE"
            }

            title.text = curMessage.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    fun addMessage(todoMessage: todoMessage) {
        messages.add(todoMessage)
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