package com.example.cocktailmenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(
    private val titles: Array<String?>,
    private val images: List<Int>?
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var imageview: ImageView
        var cardview: CardView

        init {
            //imageview = view.findViewById(R.id.imageview)
            cardview = view.findViewById(R.id.card_view)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_captioned_image, viewGroup, false)

        context = viewGroup.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val cardView: CardView = viewHolder.cardview
        //ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        //val drawable = ContextCompat.getDrawable(cardView.context, imageIds.get(position))
        //imageView.setImageDrawable(drawable)
        //imageView.setContentDescription(captions.get(position))
        val textcard = cardView.findViewById(R.id.textcard) as TextView
        textcard.text = titles[position]

        //viewHolder.imageview.setImageResource(images[position])
    }

    override fun getItemCount() = titles.size
}