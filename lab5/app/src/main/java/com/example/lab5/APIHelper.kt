package com.example.lab5

import android.util.Log
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.URL

class APIHelper {

    fun getUsers(url: String): ArrayList<ArrayList<String>> {
        var iter = 1
        val users = ArrayList<ArrayList<String>>()
        while (true) {
            try {
                val user = ArrayList<String>()
                val jsonString = URL("$url/$iter").readText()
                if (jsonString != "") {
                    val json = JSONObject(jsonString)

                    val id = json.getInt("id")
                    val name = json.getString("name")
                    val email = json.getString("email")

                    user.add(id.toString())
                    user.add(name)
                    user.add(email)
                }
                iter++
                users.add(user)
            } catch (e: FileNotFoundException) {
                break
            }
        }
        return users
    }

    fun getTodos(url: String): ArrayList<ArrayList<String>> {
        val todos = ArrayList<ArrayList<String>>()
        var iter = 1
        while (true) {
            try {
                val todo = ArrayList<String>()
                val jsonString = URL("$url/$iter").readText()
                if (jsonString != "") {
                    val json = JSONObject(jsonString)

                    val userID = json.getInt("userId")
                    val title = json.getString("title")
                    val completed = json.getBoolean("completed")

                    todo.add(userID.toString())
                    todo.add(title)
                    todo.add(completed.toString())
                }
                iter++
                todos.add(todo)
            } catch (e: FileNotFoundException) {
                break
            }
        }
        return todos
    }
}