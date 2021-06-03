package com.example.cocktailmenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(
    private val titles: Array<String?>,
    private val images: Array<Int?>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardview: CardView = view.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_captioned_image, viewGroup, false)

        context = viewGroup.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cardView: CardView = viewHolder.cardview
        val imageView: ImageView = cardView.findViewById(R.id.info_image) as ImageView

        val id = images[position]
        val img = cardView.context.resources.getIdentifier(
            "a$id",
            "drawable",
            cardView.context.packageName
        )
        val drawable = images.let { ContextCompat.getDrawable(cardView.context, img) }!!
        imageView.contentDescription = titles[position]
        imageView.setImageDrawable(drawable)
        val textcard = cardView.findViewById(R.id.textcard) as TextView
        textcard.text = titles[position]
    }

    override fun getItemCount() = titles.size
}