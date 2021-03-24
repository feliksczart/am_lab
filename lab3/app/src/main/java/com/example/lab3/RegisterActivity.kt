package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private val TAG = "RegisterActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        val registerButton = findViewById<Button>(R.id.registerButton)
        val rankingButton = findViewById<TextView>(R.id.rankingButton)
        val loginText = findViewById<TextView>(R.id.loginText)
        val username = findViewById<EditText>(R.id.nickRegister)
        val password = findViewById<EditText>(R.id.passwordRegister)
        val rePassword = findViewById<EditText>(R.id.repeatPasswordRegister)
        val myDB = DBHelper(this)

        registerButton.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()
            val rePass = rePassword.text.toString()

            if (user == "" || pass == "" || rePass == "") {
                Toast.makeText(applicationContext, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (pass == rePass) {
                    val userCheck = myDB.checkUserName(user)
                    if (userCheck == false) {
                        val registrationResult = myDB.insertData(user, pass)
                        if (registrationResult) {
                            Toast.makeText(
                                applicationContext,
                                "Rejestracja przebiegła pomyślnie",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Rejestracja nie powiodła się",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Użytkownik o podanej nazwie \n już istnieje.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Hasła muszą być takie same!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        rankingButton.setOnClickListener{
            val intent = Intent(this, RankingActivity::class.java)
            intent.putExtra("From", "Register")
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