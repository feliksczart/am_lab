package com.example.lab5

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoaderActivity : AppCompatActivity() {
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: LinearLayout
    private val hideHandler = Handler()

    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        fullscreenContent.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreenContentControls.visibility = View.VISIBLE
    }
    private var isFullscreen: Boolean = false

    private val hideRunnable = Runnable { hide() }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @ExperimentalStdlibApi
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.mylightdark)
        supportActionBar?.hide()

        val loadingCircle = findViewById<ProgressBar>(R.id.loading)
        val myDB = DBHelper(this)
        val myDBwritable = myDB.writableDatabase
//        myDBwritable.execSQL("drop table if exists users")
//        myDBwritable.execSQL("drop table if exists todos")
//        myDBwritable.execSQL("drop table if exists posts")
//        myDBwritable.execSQL("drop table if exists comments")
//        myDBwritable!!.execSQL("create table users(id number primary key, name text, mail text);")
//        myDBwritable!!.execSQL("create table todos(id number primary key, userId number, title text, completed text, foreign key(userId) references users(id));")
//        myDBwritable!!.execSQL("create table posts(id number primary key, userId number, title text, body text, foreign key(userId) references users(id));")
//        myDBwritable!!.execSQL("create table comments(id number primary key, postId number, name text, mail text, body text, foreign key(postId) references posts(id));")

        val apiHelper = APIHelper()
        val allThreadsDone = 0
        Thread {
            run {
//                val timeInMillis = measureTimeMillis {
//                }
//                Thread.sleep(3000 - timeInMillis)

                val users = apiHelper.getUsers("http://jsonplaceholder.typicode.com/users")
                val mydb = DBHelper(this)
                for (user in users) {
                    if (mydb.checkUser(user[0])) {
                        mydb.insertUser(user)
                    }
                }

                val todos = apiHelper.getTodos("http://jsonplaceholder.typicode.com/todos")
                for (todo in todos) {
                    if (mydb.checkTodo(todo[0])) {
                        mydb.insertTodo(todo)
                    }
                }

                val posts = apiHelper.getPosts("http://jsonplaceholder.typicode.com/posts")
                for (post in posts) {
                    if (mydb.checkPost(post[0])) {
                        mydb.insertPost(post)
                    }
                }

                val comms = apiHelper.getComms("http://jsonplaceholder.typicode.com/comments")
                for (comm in comms) {
                    if (mydb.checkComment(comm[0])) {
                        mydb.insertComment(comm)
                    }
                }
            }
            runOnUiThread {
                myDB.close()
                loadingCircle?.visibility = View.GONE
                val intent = Intent(this, UsersActivity::class.java)
                startActivity(intent)
                Thread.sleep(100)
                finish()
            }
        }.start()


        setContentView(R.layout.activity_loader)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isFullscreen = true

        // Set up the user interaction to manually show or hide the system UI.
        fullscreenContent = findViewById(R.id.fullscreen_content)
        fullscreenContent.setOnClickListener { toggle() }

        fullscreenContentControls = findViewById(R.id.fullscreen_content_controls)

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(10)
    }

    private fun toggle() {
        if (isFullscreen) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreenContentControls.visibility = View.GONE
        isFullscreen = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        fullscreenContent.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        isFullscreen = true

        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300
    }
}