package com.example.cocktailmenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_COCKTAIL_ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val frag =
            supportFragmentManager.findFragmentById(R.id.detail_frag) as CocktailDetailFragment?

        val cocktailId = intent.extras!![EXTRA_COCKTAIL_ID] as Int
        frag!!.setCocktail(cocktailId)
    }
}