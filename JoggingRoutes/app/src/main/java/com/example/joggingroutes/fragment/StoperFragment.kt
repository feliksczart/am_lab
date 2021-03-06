package com.example.joggingroutes.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.joggingroutes.DBHelper
import com.example.joggingroutes.R
import com.example.joggingroutes.activity.MainActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


@Suppress("DEPRECATION")
class StoperFragment : Fragment(), View.OnClickListener {
    var seconds: Int = 0
    var running: Boolean = false
    var wasRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        SavedInstanceState: Bundle?
    ): View {
        val layout: View = inflater.inflate(R.layout.fragment_stoper, container, false)
        runStoper(layout)
        val startButton: Button = layout.findViewById<View>(R.id.start_button) as Button
        startButton.setOnClickListener(this)
        val stopButton: Button = layout.findViewById<View>(R.id.stop_button) as Button
        stopButton.setOnClickListener(this)
        val resetButton: Button = layout.findViewById<View>(R.id.reset_button) as Button
        resetButton.setOnClickListener(this)
        val saveButton: Button = layout.findViewById<View>(R.id.save_button) as Button
        saveButton.setOnClickListener(this)
        return layout
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()

        if (wasRunning) {
            running = true
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putInt("seconds", seconds)
        savedInstanceState.putBoolean("running", running)
        savedInstanceState.putBoolean("wasRunning", wasRunning)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.start_button -> onClickStart()
            R.id.stop_button -> onClickStop()
            R.id.reset_button -> onClickReset()
            R.id.save_button -> onClickSave()
        }
    }

    private fun onClickStart() {
        running = true
    }

    private fun onClickStop() {
        running = false
    }

    private fun onClickReset() {
        running = false
        seconds = 0
    }


    @SuppressLint("SimpleDateFormat")
    private fun onClickSave() {
        val saveSeconds = seconds.toString()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val saveDate = sdf.format(Date())
        val route = RouteDetailFragment.routeName
        val username = MainActivity.username
        insertData(username,route,saveSeconds,saveDate)
    }

    private fun runStoper(view: View) {
        val timeView = view.findViewById<View>(R.id.time_view) as TextView
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                val time = String.format("%d:%02d:%02d", hours, minutes, secs)
                timeView.text = time
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun insertData(username: String,route: String,saveSeconds: String,saveDate:String) {
        val method = "insert result"
        val dbHelper = context?.let { DBHelper(it) }
        dbHelper?.execute(method, username, route,saveSeconds,saveDate)
    }
}