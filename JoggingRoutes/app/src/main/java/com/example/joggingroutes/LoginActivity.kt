package com.example.joggingroutes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var dbUsername: String
    lateinit var dbPassword: String
    lateinit var username: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()
        window.decorView.setBackgroundColor(Color.BLACK)

        username = findViewById(R.id.nickLogin)
        password = findViewById(R.id.passwordLogin)
        val registerButton = findViewById<TextView>(R.id.goRegister)
        val loginButton = findViewById<Button>(R.id.loginButton)

        registerButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun loguser(view: View) {
            dbUsername = username.text.toString()
            dbPassword = password.text.toString()

            val method = "login"
            val dbHelper = DBHelper(this)
            dbHelper.execute(method, dbUsername, dbPassword)
            //finish()
    }
}