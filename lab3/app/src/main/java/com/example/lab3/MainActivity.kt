package com.example.lab3

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var score = 0
    var shotCount = 0
    var drawnNumber = 0
    var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val renewButton = findViewById<ImageButton>(R.id.renewButton)
        renewButton.drawable.mutate().setColorFilter(0xFF9871DE.toInt(), PorterDuff.Mode.MULTIPLY)

        draw()

        val guessButton = findViewById<Button>(R.id.guessButton)
        val yourScore = findViewById<TextView>(R.id.yourScore)
        val input = findViewById<EditText>(R.id.editText)
        val drawnCount = findViewById<TextView>(R.id.drawnCount)
        val zeroButton = findViewById<Button>(R.id.zeroButton)

        guessButton.setOnClickListener {
            val number = input.text.toString().toInt()

            val count = check(input, number, yourScore, drawnCount)
            if (count == 0) drawnCount.text = ""
            else drawnCount.text = count.toString()

            input.text.clear()
        }

        renewButton.setOnClickListener {
            reset(input, yourScore, drawnCount)
        }

        zeroButton.setOnClickListener{
            score = 0
            yourScore.text = "0"
        }
    }

    fun draw() {
        shotCount = 0
        drawnNumber = Random.nextInt(0, 20)
    }

    fun scoring(input: EditText, yourScore: TextView, drawnCount: TextView) {
        var pts = 0
        when (shotCount) {
            1 -> pts = 5
            2 -> pts = 3
            3 -> pts = 3
            4 -> pts = 3
            5 -> pts = 2
            6 -> pts = 2
            7 -> pts = 1
            8 -> pts = 1
            9 -> pts = 1
            10 -> pts = 1
            else -> {
//                showLost();
                reset(input, yourScore,drawnCount)
            }
        }
        points = pts
    }

    private fun check(input: EditText, number: Int, yourScore: TextView, drawnCount: TextView): Int {
        if (number == drawnNumber) {
            scoring(input, yourScore, drawnCount)
            Toast.makeText(applicationContext, "Trafiłeś! Zdobywasz " + points + " punktów", Toast.LENGTH_LONG).show()
            score += points
            yourScore.text = "$score"
            draw()
        } else {
            if (number < drawnNumber) {
                Toast.makeText(applicationContext, "Daj więcej", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Daj mniej", Toast.LENGTH_LONG).show()
            }
            shotCount++
            if (shotCount > 10){
                scoring(input, yourScore, drawnCount)
            }
        }
        return shotCount
    }

    private fun reset(input: EditText, yourScore: TextView, drawnCount: TextView) {
        input.text.clear()
        drawnCount.text = ""
        score = 0
        yourScore.text = "0"
        draw()
    }
}