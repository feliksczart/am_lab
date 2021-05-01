package com.example.joggingroutes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        val EXTRA_COCKTAIL_ID = "id"
        val EXTRA_COORDS = "coords"
    }

    lateinit var map: GoogleMap
    lateinit var place1: MarkerOptions
    lateinit var place2: MarkerOptions
    lateinit var coords: MutableList<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_frag) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        place1 = MarkerOptions().position(LatLng(52.38327, 16.97765)).title("Location 1")
        place2 = MarkerOptions().position(LatLng(52.38396, 16.9785)).title("Location 2")

        coords = intent.extras!![EXTRA_COORDS] as MutableList<LatLng>
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        for (i in 0..coords.size - 2) {
            val line = PolylineOptions().add(coords[i], coords[i + 1])
                .width(7f).color(Color.RED)
            map.addPolyline(line)
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords[0], 16f))
    }
}