package com.example.joggingroutes.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.TestLooperManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.joggingroutes.DBHelper
import com.example.joggingroutes.R
import com.example.joggingroutes.activity.MainActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import kotlin.properties.Delegates


@Suppress("DEPRECATION")
class RouteDetailFragment : Fragment(), OnMapReadyCallback {
    private var routeId by Delegates.notNull<Int>()
    lateinit var coords: MutableList<LatLng>
    lateinit var map: GoogleMap

    companion object {
        lateinit var routeName: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            routeId = savedInstanceState.getLong("routeId").toInt()
        } else {
            val stoper = StoperFragment()
            val ft: FragmentTransaction = childFragmentManager.beginTransaction()
            ft.add(R.id.stoper_container, stoper)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }
    }

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
            val route: Route = Route.routes[routeId]
            title.text = route.getName()
            routeName = route.getName()
            title.setBackgroundColor(Color.parseColor("#000000"))
            val description = view.findViewById<View>(R.id.textDescription) as TextView
            description.text = route.getLength()
            description.setBackgroundColor(Color.parseColor("#000000"))

            val mapFragment = childFragmentManager.findFragmentById(R.id.map_frag_tab) as? SupportMapFragment
            mapFragment?.getMapAsync(this)
            coords = route.getRouteCoords()

            val personalBest = view.findViewById<View>(R.id.personal_best) as TextView
            val personalLast = view.findViewById<View>(R.id.personal_last) as TextView

            val respond = getResults(MainActivity.username,route.getName())
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("routeId", routeId.toLong())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        for (i in 0 .. coords.size-2){
            val line = PolylineOptions().add(coords[i], coords[i + 1])
                .width(7f).color(Color.RED)
            map.addPolyline(line)
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords[0], 16f))
    }

    fun getResults(username: String, routeName: String){
        val method = "get results"
        val dbHelper = context?.let { DBHelper(it) }
        dbHelper?.execute(method, username, routeName)
    }
}