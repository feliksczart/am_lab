package com.example.cocktailmenu

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlin.properties.Delegates


@Suppress("DEPRECATION")
class LauncherActivity : AppCompatActivity() {

    lateinit var lantext: TextView
    lateinit var top: ImageView
    lateinit var heart: ImageView
    lateinit var beat: ImageView
    lateinit var bot: ImageView

    lateinit var chSequence: CharSequence
    var id by Delegates.notNull<Int>()
    val delay: Long = 200
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()

        lantext = findViewById(R.id.lan_text)
        top = findViewById(R.id.iv_top)
        heart = findViewById(R.id.iv_heart)
        beat = findViewById(R.id.iv_beat)
        bot = findViewById(R.id.iv_bot)

        initAnimation()

        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val ni = manager.activeNetworkInfo
        val connected = ni != null && ni.isConnected
        if (connected) {

            val ordinaryGetter = DrinksGetter(this)
            ordinaryGetter.execute()

        } else {
            finish()
        }
    }

    fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun initAnimation(){
        val topAnim: Animation = AnimationUtils.loadAnimation(this,R.anim.top_wave)
        top.animation = topAnim

        val objectAnimator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            heart,
            PropertyValuesHolder.ofFloat("scaleX",1.2f),
            PropertyValuesHolder.ofFloat("scaleY",1.2f),
        )
        objectAnimator.duration = 500
        objectAnimator.repeatCount = ValueAnimator.INFINITE
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.start()

        animateText("Loading Data")

        val botAnim: Animation = AnimationUtils.loadAnimation(this,R.anim.bot_wave)
        bot.animation = botAnim
    }

    private val runnable = object : Runnable {
        override fun run() {
            lantext.text = chSequence.subSequence(0,id++)
            if (id <= chSequence.length){
                handler.postDelayed(this,delay)
            }
        }
    }

    fun animateText(cs: CharSequence){
        chSequence = cs
        id = 0
        lantext.text = ""
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable,delay)
    }
}
