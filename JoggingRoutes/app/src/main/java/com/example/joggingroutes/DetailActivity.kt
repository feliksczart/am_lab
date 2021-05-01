package com.example.joggingroutes

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.directions.route.*
import com.directions.route.Route
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions


//import android.support.v7.app.AppCompatActivity;


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity(), OnMapReadyCallback, RoutingListener {
    companion object {
        val EXTRA_COCKTAIL_ID = "id"
    }

    lateinit var map: GoogleMap
    lateinit var mMap: GoogleMap
    lateinit var button: Button
    lateinit var place1: MarkerOptions
    lateinit var place2: MarkerOptions
    lateinit var polyLines: ArrayList<Polyline>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        button = findViewById(R.id.btnGetDirection)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_frag) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        place1 = MarkerOptions().position(LatLng(52.38327, 16.97765)).title("Location 1")
        place2 = MarkerOptions().position(LatLng(52.38396, 16.9785)).title("Location 2")

//        val frag: RouteDetailFragment? =
//            supportFragmentManager.findFragmentById(R.id.map_frag) as RouteDetailFragment?

//        val routeId = intent.extras!![EXTRA_COCKTAIL_ID] as Int
//        frag!!.setRoute(routeId.toLong())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
//        map.addMarker(place1)
//        map.addMarker(place2)
        FindRoutes(place1.position,place2.position)
    }

    fun FindRoutes(start: LatLng, end: LatLng){
        val routing: Routing = Routing.Builder()
            .travelMode(AbstractRouting.TravelMode.BIKING)
            .withListener(this)
            .alternativeRoutes(false)
            .waypoints(start,end)
            .key(R.string.google_maps_key.toString())
            .build()
        routing.execute()

    }

    override fun onRoutingFailure(p0: RouteException?) {
        Toast.makeText(applicationContext,"Routing Failed", Toast.LENGTH_SHORT).show()
        FindRoutes(place1.position,place2.position)
    }

    override fun onRoutingStart() {
        Toast.makeText(this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {
        val center: CameraUpdate = CameraUpdateFactory.newLatLng(place1.position)
        val zoom: CameraUpdate = CameraUpdateFactory.zoomTo(16F)

        if (polyLines != null){
            polyLines.clear()
        }

        val polyOptions = PolylineOptions()
        var polyStart: LatLng? = null
        var polyEnd: LatLng? = null

        polyLines = ArrayList()

        for (i in 0..route!!.size){
            if (i==shortestRouteIndex){
                polyOptions.color(Color.BLUE)
                polyOptions.width(7F)
                polyOptions.addAll(route[shortestRouteIndex].points)
                val polyLine: Polyline = map.addPolyline(polyOptions)
                polyStart = polyLine.points[0]
                val k = polyLine.points.size
                polyEnd = polyLine.points[k-1]
                polyLines.add(polyLine)
            }
        }
        val endMarker = MarkerOptions()
        endMarker.position(polyEnd)
        map.addMarker(endMarker)
    }

    override fun onRoutingCancelled() {
        FindRoutes(place1.position,place2.position)
    }
}