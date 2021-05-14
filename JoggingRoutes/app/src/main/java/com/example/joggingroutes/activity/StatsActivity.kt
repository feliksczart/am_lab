package com.example.joggingroutes.activity

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.joggingroutes.R
import java.lang.Exception

@Suppress("DEPRECATION")
class StatsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()
        window.decorView.setBackgroundColor(Color.BLACK)

        val personalBest = findViewById<TextView>(R.id.personal_best)
        val personalLast = findViewById<TextView>(R.id.personal_last)
        val all_res = findViewById<TextView>(R.id.all_res)
        val res = intent.getStringExtra("result")?.split(";")
        try {
            val best_res = res?.get(0)?.split(",")?.get(0)
            val best_date = res?.get(0)?.split(",")?.get(1)

            val last_res = res?.get(1)?.split(",")?.get(0)
            val last_date = res?.get(1)?.split(",")?.get(1)

            var all = ""
            try {
                for (i in res?.get(2)?.split('.')!!){
                    val a = i.split(',')
                    val name = a[0]
                    val sec = a[1]
                    val dat = a[2]
                    all += "    $name:  $sec"+"s   $dat\n"
                }
            } catch (e: Exception){}

            personalBest.text = "Time: $best_res"+"s    Date: $best_date"
            personalLast.text = "Time: $last_res"+"s    Date: $last_date"
            all_res.text = all

        } catch (e: Exception){
            personalBest.text = "You have never run this route"
        }
    }
}