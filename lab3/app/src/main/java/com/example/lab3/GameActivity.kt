package com.example.lab3

import android.content.DialogInterface
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class GameActivity : AppCompatActivity() {

    var score = 0
    var shotCount = 0
    var drawnNumber = 0
    var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()

        val renewButton = findViewById<ImageButton>(R.id.renewButton)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            renewButton.drawable.colorFilter =
                BlendModeColorFilter(Color.parseColor("#54309a"), BlendMode.SRC_ATOP)
        }

        draw()

        val guessButton = findViewById<Button>(R.id.guessButton)
        val yourScore = findViewById<TextView>(R.id.yourScore)
        val input = findViewById<EditText>(R.id.editText)
        val drawnCount = findViewById<TextView>(R.id.drawnCount)

        guessButton.setOnClickListener {
            val inputText = input.text.toString()

            val number = if (inputText == "") {
                100
            } else {
                inputText.toInt()
            }

            val count = check(input, number, yourScore, drawnCount)
            if (count == 0) drawnCount.text = ""
            else if (count in 1..20) drawnCount.text = count.toString()

            input.text.clear()
        }

        renewButton.setOnClickListener {
            resetDialog(input, yourScore, drawnCount)
        }
    }

    private fun draw() {
        shotCount = 0
        drawnNumber = Random.nextInt(0, 20)
    }

    private fun scoring(input: EditText, yourScore: TextView, drawnCount: TextView) {
        var pts = 0
        when (shotCount + 1) {
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
                loseDialog(input, yourScore, drawnCount)
            }
        }
        points = pts
    }

    private fun check(
        input: EditText,
        number: Int,
        yourScore: TextView,
        drawnCount: TextView
    ): Int? {

        when (number) {
            drawnNumber -> {
                scoring(input, yourScore, drawnCount)
                winDialog()
                score += points
                yourScore.text = "$score"
                draw()
            }
            in 0..20 -> {
                if (number < drawnNumber) {
                    val toast =
                        Toast.makeText(applicationContext, "Daj więcej", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()

                } else {
                    val toast =
                        Toast.makeText(applicationContext, "Daj mniej", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()
                }
                shotCount++
                if (shotCount > 10) {
                    scoring(input, yourScore, drawnCount)
                }
            }
            else -> {
                println()
                val toast = Toast.makeText(
                    applicationContext,
                    "Liczba musi być z przedziału od 0 do 20!",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.TOP, 0, 0)
                toast.show()
                return null
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

    private fun loseDialog(input: EditText, yourScore: TextView, drawnCount: TextView) {
        val builder = AlertDialog.Builder(this@GameActivity)
        builder.setTitle("PRZEGRAŁEŚ!")
        builder.setMessage("Wykorzystałeś wsztstkie możliwe strzały.")

        builder.setPositiveButton("OK") { _: DialogInterface?, _: Int ->
            reset(input, yourScore, drawnCount)
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun winDialog() {
        val builder = AlertDialog.Builder(this@GameActivity)
        val x = shotCount + 1
        builder.setTitle("WYGRAŁEŚ!")
        builder.setMessage("Udało Ci się zgadnąć za $x razem.")

        builder.setPositiveButton("OK") { _: DialogInterface?, _: Int -> }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun resetDialog(input: EditText, yourScore: TextView, drawnCount: TextView) {
        val builder = AlertDialog.Builder(this@GameActivity)
        builder.setTitle("UWAGA!")
        builder.setMessage("Czy na pewno chcesz rozpocząć nową grę?")

        builder.setPositiveButton("YES") { _: DialogInterface?, _: Int ->
            reset(input, yourScore, drawnCount)
        }

        builder.setNegativeButton("NO") { _: DialogInterface?, _: Int -> }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}