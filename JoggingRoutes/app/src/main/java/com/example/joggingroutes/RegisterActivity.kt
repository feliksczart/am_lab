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
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()
        window.decorView.setBackgroundColor(Color.BLACK)

        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginButton = findViewById<TextView>(R.id.goLogin)
        val username = findViewById<EditText>(R.id.nickRegister)
        val password = findViewById<EditText>(R.id.passwordRegister)
        val rePassword = findViewById<EditText>(R.id.repeatPasswordRegister)

        loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}