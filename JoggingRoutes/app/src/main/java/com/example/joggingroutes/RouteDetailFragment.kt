package com.example.joggingroutes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
//import android.support.v4.app.Fragment
import kotlin.properties.Delegates


class RouteDetailFragment : Fragment() {
    private var routeId by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_route_detail, container, false)
    }

    fun setRoute(id: Long) {
        this.routeId = id.toInt()
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val title = view.findViewById<View>(R.id.textTitle) as TextView
            val route: Route = Route.routes[routeId.toInt()]
            title.text = route.getName()
            val description = view.findViewById<View>(R.id.textDescription) as TextView
            description.text = route.getRoute()
        }
    }


}