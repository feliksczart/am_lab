package com.example.cocktailmenu

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_COCKTAIL_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        val frag =
            supportFragmentManager.findFragmentById(R.id.detail_frag) as CocktailDetailFragment?

        val cocktailId = intent.extras!![EXTRA_COCKTAIL_ID] as Int
        frag!!.setCocktail(cocktailId)

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBarDetails)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ic_mainScreen -> {
                    true
                }
                R.id.icCocktails -> {
                    // Handle search icon press
                    true
                }
                R.id.icOrdinary -> {
                    // Handle more item (inside overflow menu) press
                    true
                }
                else -> false
            }
        }
    }
}