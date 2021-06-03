package com.example.cocktailmenu

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val ni = manager.activeNetworkInfo
        val connected = ni != null && ni.isConnected
        if (connected) {

            val ordinaryGetter = DrinksGetter(this)
            //val listFolderGetter = ListFolderGetter(this)

            ordinaryGetter.execute()
            //listPdfGetter.execute()
        } else {
            finish()
        }
    }

    fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
