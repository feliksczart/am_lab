package com.example.cocktailmenu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_COCKTAIL_ID = "id"
    }

    var inRecipe = false
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        val cocktailId = intent.extras!![EXTRA_COCKTAIL_ID] as Int

        if (savedInstanceState != null) {
            inRecipe = savedInstanceState.getBoolean("inRecipe")
            if (inRecipe){
                alertDialog = AlertDialog.Builder(this).create()
                inRecipe = true
                alertDialog.setTitle("Przepis")
                alertDialog.setMessage(DrinksGetter.cocktails[cocktailId].recipe)
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL,
                    "Back"
                ) { dialog, _ -> dialog.dismiss()
                    inRecipe = false
                }
                alertDialog.show()
            }
        }

        val frag =
            supportFragmentManager.findFragmentById(R.id.detail_frag) as CocktailDetailFragment?

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

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            alertDialog = AlertDialog.Builder(this).create()
            inRecipe = true
            alertDialog.setTitle("Przepis")
            if (MainActivity.CURR_CARD == "ord")
                alertDialog.setMessage(DrinksGetter.cocktails[cocktailId].recipe)
            else if (MainActivity.CURR_CARD == "cock")
                alertDialog.setMessage(DrinksGetter.cocktails[cocktailId+10].recipe)
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                "Back"
            ) { dialog, _ -> dialog.dismiss()
                inRecipe = false
            }
            alertDialog.show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (inRecipe) alertDialog.dismiss()
        outState.putBoolean("inRecipe",inRecipe)
    }
}