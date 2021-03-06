package com.example.joggingroutes.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.example.joggingroutes.R
import com.google.android.gms.maps.model.LatLng


class RouteListFragment : ListFragment() {
    interface Listener {
        fun itemClicked(id: Long, coords: MutableList<LatLng>, routeName:String)
    }

    private var listener: Listener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val names = arrayOfNulls<String>(Route.routes.size)
        for (i in names.indices) {
            names[i] = Route.routes[i].getName()
        }
        val adapter: Any = ArrayAdapter<Any?>(
            inflater.context, R.layout.my_list_item, names
        )
        listAdapter = adapter as ListAdapter?

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        listener?.itemClicked(id,Route.routes[id.toInt()].getRouteCoords(),Route.routes[id.toInt()].getName())
    }
}