package com.example.cocktailmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

//https://stackoverflow.com/questions/32700818/how-to-open-a-fragment-on-button-click-from-a-fragment-in-android
@Suppress("DEPRECATION")
class OrdinaryListActivity : AppCompatActivity(), CocktailListFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordinary_list)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)

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

    override fun itemClicked(id: Long) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_COCKTAIL_ID, id.toInt())
        startActivity(intent)
    }
}