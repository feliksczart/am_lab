package com.example.joggingroutes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import android.support.v7.app.AppCompatActivity;


class DetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_COCKTAIL_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val frag: RouteDetailFragment? =
            supportFragmentManager.findFragmentById(R.id.detail_frag) as RouteDetailFragment?

        val routeId = intent.extras!![EXTRA_COCKTAIL_ID] as Int
        frag!!.setRoute(routeId.toLong())
    }
}