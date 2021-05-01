//http://www.runmap.net/en/regional/Poland/Greater+Poland+Voivodeship/Pozna%C5%84/
package com.example.joggingroutes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
//import android.support.v7.app.AppCompatActivity;
//import android.support.v4.app.FragmentTransaction;


class MainActivity : AppCompatActivity(), RouteListFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun itemClicked(id: Long) {
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
            startActivity(intent)
        }
    }
}