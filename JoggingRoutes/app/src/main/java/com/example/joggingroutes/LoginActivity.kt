package com.example.joggingroutes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()
        window.decorView.setBackgroundColor(Color.BLACK)

        val username = findViewById<EditText>(R.id.nickLogin)
        val password = findViewById<EditText>(R.id.passwordLogin)
        val registerButton = findViewById<TextView>(R.id.goRegister)
        val loginButton = findViewById<Button>(R.id.loginButton)

        registerButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}