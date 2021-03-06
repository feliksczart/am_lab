package com.example.joggingroutes.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.joggingroutes.DBHelper
import com.example.joggingroutes.R

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    lateinit var dbUsername: String
    lateinit var dbPassword: String
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var rePassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar?.hide()
        window.decorView.setBackgroundColor(Color.BLACK)

        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginButton = findViewById<TextView>(R.id.goLogin)
        username = findViewById(R.id.nickRegister)
        password = findViewById(R.id.passwordRegister)
        rePassword = findViewById(R.id.repeatPasswordRegister)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun reguser(view: View) {
        if (rePassword.text.toString() == password.text.toString() && password.text.toString() != "" && username.text.toString() != "") {
            dbUsername = username.text.toString()
            dbPassword = password.text.toString()

            val method = "register"
            val dbHelper = DBHelper(this)
            dbHelper.execute(method, dbUsername, dbPassword)
            finish()
        } else {
            Toast.makeText(applicationContext,"Correct the mistake!",Toast.LENGTH_SHORT).show()
        }
    }
}