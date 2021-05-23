package com.example.cocktailmenu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment


class CocktailListFragment : ListFragment() {

    interface Listener {
        fun itemClicked(id: Long)
    }
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val names = arrayOfNulls<String>(Cocktail.cocktails.size)
        for (i in names.indices) {
            names[i] = Cocktail.cocktails[i].getName()
        }
        val adapter: Any = ArrayAdapter<Any?>(
            inflater.context, android.R.layout.simple_list_item_1, names
        )
        listAdapter = adapter as ListAdapter?

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        listener?.itemClicked(id)
    }
}