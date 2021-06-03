package com.example.cocktailmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates


class CocktailListFragment : Fragment() {

    private var myAdapter: RecyclerView.Adapter<*>? = null
    var ordLoaded: Boolean = false
    var IS_EDITING_KEY by Delegates.notNull<Boolean>()

    interface Listener {
        fun itemClicked(id: Long)
    }

    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragview: RecyclerView =
            inflater.inflate(R.layout.recycler2, container, false) as RecyclerView

        val names = arrayOfNulls<String>(DrinksGetter.cocktails.size / 2)
        val cocktailImages = arrayOfNulls<Int>(DrinksGetter.cocktails.size / 2)
        if (!MainActivity.ORD_LOADED) {
            for (i in names.indices) {
                names[i] = DrinksGetter.cocktails[i].getName()
                cocktailImages[i] = DrinksGetter.cocktails[i].getImageResourceId()
            }
        } else {
            for (i in names.indices) {
                names[i] = DrinksGetter.cocktails[i + 10].getName()
                cocktailImages[i] = DrinksGetter.cocktails[i + 10].getImageResourceId()
            }
        }

        val gridManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        myAdapter = CustomAdapter(names, cocktailImages)
        fragview.adapter = myAdapter
        fragview.layoutManager = gridManager
        fragview.hasFixedSize()

        addClickListener(fragview)

        MainActivity.ORD_LOADED = true

        return fragview
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    fun addClickListener(recycler: RecyclerView) {
        recycler.addOnItemTouchListener(
            RecyclerItemClickListenr(
                context,
                recycler,
                object : RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_COCKTAIL_ID, position)
                        startActivity(intent)
                    }
                })
        )
    }
}