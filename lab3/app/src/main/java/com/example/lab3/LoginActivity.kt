package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerText = findViewById<TextView>(R.id.registerText)
        val username = findViewById<EditText>(R.id.nickLogin)
        val password = findViewById<EditText>(R.id.passwordLogin)
        val myDB = DBHelper(this)

        loginButton.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user == "" || pass == "") {
                Toast.makeText(applicationContext, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val checkResult = myDB.checkUserPassword(user,pass)
                if (checkResult){
                    val intent = Intent(this, GameActivity::class.java)
                    startActivity(intent)
                    finish()
                } else{
                    Toast.makeText(applicationContext, "Błąd logowania! \n Sprawdz login i hasło.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        registerText.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}