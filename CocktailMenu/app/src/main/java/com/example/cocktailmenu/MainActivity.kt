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

    companion object{
        var ORD_LOADED: Boolean = false
        var ORD_LOADED_DETAILS: Boolean = false
        var CURR_CARD = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val mainFrag = findViewById<FragmentContainerView>(R.id.fragment_container_main)
        val ordinaryFrag = findViewById<FragmentContainerView>(R.id.fragment_container)
        val cocktailFrag = findViewById<FragmentContainerView>(R.id.fragment_container_cocktail)

        mMyFragment = "main"

        if (savedInstanceState != null) {
            mMyFragment = savedInstanceState.getString("fragment").toString()
            if (mMyFragment == "main"){
                mainFrag.visibility = View.VISIBLE
                ordinaryFrag.visibility = View.GONE
                cocktailFrag.visibility = View.GONE
                CURR_CARD = "main"
            } else if (mMyFragment == "ord"){
                mainFrag.visibility = View.GONE
                ordinaryFrag.visibility = View.VISIBLE
                cocktailFrag.visibility = View.GONE
                CURR_CARD = "ord"
            }
            else if (mMyFragment == "cock"){
                mainFrag.visibility = View.GONE
                ordinaryFrag.visibility = View.GONE
                cocktailFrag.visibility = View.VISIBLE
                CURR_CARD = "cock"
            }
        }

        topAppBar.setNavigationOnClickListener {

        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ic_mainScreen -> {
                    mainFrag.visibility = View.VISIBLE
                    ordinaryFrag.visibility = View.GONE
                    cocktailFrag.visibility = View.GONE
                    mMyFragment = "main"
                    CURR_CARD = "main"
                }
                R.id.icOrdinary -> {
                    mainFrag.visibility = View.GONE
                    ordinaryFrag.visibility = View.VISIBLE
                    cocktailFrag.visibility = View.GONE
                    mMyFragment = "ord"
                    CURR_CARD = "ord"
                }
                R.id.icCocktails -> {
                    mainFrag.visibility = View.GONE
                    ordinaryFrag.visibility = View.GONE
                    cocktailFrag.visibility = View.VISIBLE
                    mMyFragment = "cock"
                    CURR_CARD = "cock"
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