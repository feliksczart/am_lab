package com.example.joggingroutes

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import java.util.*


//import android.support.v7.app.AppCompatActivity;


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        val EXTRA_COCKTAIL_ID = "id"
    }

    lateinit var map: GoogleMap
    lateinit var mMap: GoogleMap
    lateinit var button: Button
    lateinit var place1: MarkerOptions
    lateinit var place2: MarkerOptions
    lateinit var polyLines: ArrayList<Polyline>
    lateinit var markerPoints: ArrayList<LatLng>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()

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
        val line = PolylineOptions().add(place1.position,place2.position)
            .width(7f).color(Color.RED)

        map.addPolyline(line)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place1.position, 16f))
    }
}