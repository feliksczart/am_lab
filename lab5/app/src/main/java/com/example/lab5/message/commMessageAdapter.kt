package com.example.lab5.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.R

class commMessageAdapter(
    private val messages: MutableList<commMessage>
) : RecyclerView.Adapter<commMessageAdapter.messageViewHolder>() {
    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.commOwner)
        val mail = itemView.findViewById<TextView>(R.id.commMail)
        val body = itemView.findViewById<TextView>(R.id.commBody)

        fun bind(curMessage: commMessage){
            name.text = curMessage.name
            mail.text = curMessage.mail
            body.text = curMessage.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )
    }

    fun addMessage(commMessage: commMessage) {
        messages.add(commMessage)
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