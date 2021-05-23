package com.example.cocktailmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.appbar.MaterialToolbar

@Suppress("DEPRECATION")
class OrdinaryListActivity : AppCompatActivity(), CocktailListFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordinary_list)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val mainFrag = findViewById<FragmentContainerView>(R.id.fragment_container_main)
        val ordinaryFrag = findViewById<FragmentContainerView>(R.id.fragment_container)

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ic_mainScreen -> {
                    mainFrag.visibility = View.VISIBLE
                    ordinaryFrag.visibility = View.GONE
                }
                R.id.icOrdinary -> {
                    mainFrag.visibility = View.GONE
                    ordinaryFrag.visibility = View.VISIBLE
                }
                R.id.icCocktails -> {
                    mainFrag.visibility = View.GONE
                    ordinaryFrag.visibility = View.VISIBLE
                }
            }
            true
        }
    }

    override fun itemClicked(id: Long) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_COCKTAIL_ID, id.toInt())
        startActivity(intent)
    }
}