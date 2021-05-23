package com.example.cocktailmenu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates


class CocktailListFragment : ListFragment() {

    var myrv: RecyclerView? = null
    private var myAdapter: RecyclerView.Adapter<*>? = null
    var isEditing: Boolean = false
    var IS_EDITING_KEY by Delegates.notNull<Boolean>()

    interface Listener {
        fun itemClicked(id: Long)
    }
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO GRID
        //val rootView: View = inflater.inflate(android.R.layout.activity_list_item, container, false)
        val rootViewx: View? = super.onCreateView(inflater, container, savedInstanceState)

        val names = arrayOfNulls<String>(Cocktail.cocktails.size)
        for (i in names.indices) {
            names[i] = Cocktail.cocktails[i].getName()
        }
        val adapter: Any = ArrayAdapter<Any?>(
            inflater.context, android.R.layout.simple_list_item_1, names
        )
        listAdapter = adapter as ListAdapter?

//        myrv = rootView.findViewById(R.id.recycle_view)
//        myAdapter = CustomAdapter(names)
//        myrv!!.layoutManager = GridLayoutManager(context, 3)
//        myrv!!.adapter = myAdapter

        return rootViewx
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        listener?.itemClicked(id)
    }
}