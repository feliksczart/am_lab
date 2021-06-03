package com.example.cocktailmenu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.appbar.MaterialToolbar
import kotlin.math.abs

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class MainActivity : AppCompatActivity(), CocktailListFragment.Listener, LifecycleObserver {

    lateinit var mMyFragment: String
    lateinit var relativeLayout: RelativeLayout
    lateinit var swipeListener: SwipeListener

    companion object {
        var ORD_LOADED: Boolean = false
        var ORD_LOADED_DETAILS: Boolean = false
        var CURR_CARD = "main"
    }

    @SuppressLint("ClickableViewAccessibility")
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
        relativeLayout = findViewById(R.id.relative_layout)

        mMyFragment = "main"

        if (savedInstanceState != null) {
            mMyFragment = savedInstanceState.getString("fragment").toString()
            if (mMyFragment == "main") {
                mainFrag.visibility = View.VISIBLE
                ordinaryFrag.visibility = View.GONE
                cocktailFrag.visibility = View.GONE
                CURR_CARD = "main"
            } else if (mMyFragment == "ord") {
                mainFrag.visibility = View.GONE
                ordinaryFrag.visibility = View.VISIBLE
                cocktailFrag.visibility = View.GONE
                CURR_CARD = "ord"
            } else if (mMyFragment == "cock") {
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
        relativeLayout.setOnTouchListener(object : SwipeListener(), View.OnTouchListener {
            override fun onSwipeLeft() {
                when (CURR_CARD) {
                    "main" -> {
                        mainFrag.visibility = View.GONE
                        ordinaryFrag.visibility = View.VISIBLE
                        cocktailFrag.visibility = View.GONE
                        CURR_CARD = "ord"
                    }
                    "ord" -> {
                        mainFrag.visibility = View.GONE
                        ordinaryFrag.visibility = View.GONE
                        cocktailFrag.visibility = View.VISIBLE
                        CURR_CARD = "cock"
                    }
                    "cock" -> {
                        mainFrag.visibility = View.VISIBLE
                        ordinaryFrag.visibility = View.GONE
                        cocktailFrag.visibility = View.GONE
                        CURR_CARD = "main"
                    }
                }
            }

            override fun onSwipeRight() {
            }

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return false
            }
        })
    }

    override fun itemClicked(id: Long) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_COCKTAIL_ID, id.toInt())
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("fragment", mMyFragment)
    }


}


open class SwipeListener : GestureDetector.SimpleOnGestureListener() {

    val threshold = 100
    val velocity_threshold = 100

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val result = false
        try {
            val diffY = e2!!.y - e1!!.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > threshold && abs(velocityX) > velocity_threshold) {
                    if (diffX > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLeft()
                    }
                }
            } else {
                // onTouch(e);
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return result
    }

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}
}