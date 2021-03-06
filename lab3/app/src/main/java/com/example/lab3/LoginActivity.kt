package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val loginButton = findViewById<Button>(R.id.loginButton)
        val rankingButton = findViewById<TextView>(R.id.rankingButton)
        val registerText = findViewById<TextView>(R.id.registerText)
        val username = findViewById<EditText>(R.id.nickLogin)
        val password = findViewById<EditText>(R.id.passwordLogin)
        val myDB = DBHelper(this)

        loginButton.setOnClickListener {
            val user = username.text.toString().toLowerCase(Locale.ROOT)
            val pass = password.text.toString().toLowerCase()

            if (user == "" || pass == "") {
                Toast.makeText(applicationContext, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val checkResult = myDB.checkUserPassword(user, pass)
                if (checkResult) {
                    val intent = Intent(this, GameActivity::class.java)
                    intent.putExtra("Username", user)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Błąd logowania! \n Sprawdz login i hasło.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        rankingButton.setOnClickListener{
            val intent = Intent(this, RankingActivity::class.java)
            intent.putExtra("From", "Login")
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }
}