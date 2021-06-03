package com.example.cocktailmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class CocktailDetailFragment : Fragment() {

    private var cocktailId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            cocktailId = savedInstanceState.getLong("cocktailId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cocktail_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val title = view.findViewById<View>(R.id.textTitle) as TextView
            val cocktail: Cocktail
            if (!MainActivity.ORD_LOADED_DETAILS) {
                cocktail = DrinksGetter.cocktails[cocktailId.toInt()]
            } else{
                cocktail = DrinksGetter.cocktails[cocktailId.toInt()+10]
            }
            MainActivity.ORD_LOADED_DETAILS = true
            title.text = cocktail.getName()
            val description = view.findViewById<View>(R.id.textDescription) as TextView
            description.text = cocktail.getDetails()
        }
    }

    fun setCocktail(id: Int) {
        cocktailId = id.toLong()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("cocktailId", cocktailId)
    }
}