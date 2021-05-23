package com.example.cocktailmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.appbar.MaterialToolbar

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class MainActivity : AppCompatActivity(), CocktailListFragment.Listener, LifecycleObserver {

    lateinit var mMyFragment: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val mainFrag = findViewById<FragmentContainerView>(R.id.fragment_container_main)
        val ordinaryFrag = findViewById<FragmentContainerView>(R.id.fragment_container)
        mMyFragment = "main"

        if (savedInstanceState != null) {
            mMyFragment = savedInstanceState.getString("fragment").toString()
            if (mMyFragment == "main"){
                mainFrag.visibility = View.VISIBLE
                ordinaryFrag.visibility = View.GONE
            } else if (mMyFragment == "ord"){
                mainFrag.visibility = View.GONE
                ordinaryFrag.visibility = View.VISIBLE
            }
            else if (mMyFragment == "cock"){
                mainFrag.visibility = View.GONE
                ordinaryFrag.visibility = View.VISIBLE
            }
        }

        topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ic_mainScreen -> {
                    mainFrag.visibility = View.VISIBLE
                    ordinaryFrag.visibility = View.GONE
                    mMyFragment = "main"
                }
                R.id.icOrdinary -> {
                    mainFrag.visibility = View.GONE
                    ordinaryFrag.visibility = View.VISIBLE
                    mMyFragment = "ord"
                }
                R.id.icCocktails -> {
                    mainFrag.visibility = View.GONE
                    ordinaryFrag.visibility = View.VISIBLE
                    mMyFragment = "cock"
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("fragment",mMyFragment)
    }
}