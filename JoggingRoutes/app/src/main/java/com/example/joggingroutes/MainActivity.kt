//http://www.runmap.net/en/regional/Poland/Greater+Poland+Voivodeship/Pozna%C5%84/
package com.example.joggingroutes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.maps.model.LatLng


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), RouteListFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()
    }

    override fun itemClicked(id: Long, coords: MutableList<LatLng>) {
        val fragmentContainer = findViewById<View>(R.id.fragment_container)
        if (fragmentContainer != null) {
            val details = RouteDetailFragment()
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            details.setRoute(id)
            ft.replace(R.id.fragment_container, details)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        } else {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_COCKTAIL_ID, id.toInt())
            intent.putParcelableArrayListExtra(DetailActivity.EXTRA_COORDS, ArrayList(coords))
            startActivity(intent)
        }
    }
}