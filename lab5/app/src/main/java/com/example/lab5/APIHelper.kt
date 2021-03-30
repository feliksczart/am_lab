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

                    val id = json.getInt("id")
                    val userID = json.getInt("userId")
                    val title = json.getString("title")
                    val completed = json.getBoolean("completed")

                    todo.add(id.toString())
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

    fun getPosts(url: String): ArrayList<ArrayList<String>> {
        val posts = ArrayList<ArrayList<String>>()
        var iter = 1
        while (true) {
            try {
                val post = ArrayList<String>()
                val jsonString = URL("$url/$iter").readText()
                if (jsonString != "") {
                    val json = JSONObject(jsonString)

                    val id = json.getInt("id")
                    val userID = json.getInt("userId")
                    val title = json.getString("title")
                    val body = json.getString("body")

                    post.add(id.toString())
                    post.add(userID.toString())
                    post.add(title)
                    post.add(body.toString())
                }
                iter++
                posts.add(post)
            } catch (e: FileNotFoundException) {
                break
            }
        }
        return posts
    }

    fun getComms(url: String): ArrayList<ArrayList<String>> {
        val comms = ArrayList<ArrayList<String>>()
        var iter = 1
        while (true) {
            try {
                val comm = ArrayList<String>()
                val jsonString = URL("$url/$iter").readText()

                if (jsonString != "") {
                    val json = JSONObject(jsonString)

                    val id = json.getInt("id")
                    val postID = json.getInt("postId")
                    val name = json.getString("name")
                    val mail = json.getString("email")
                    val body = json.getString("body")

                    comm.add(id.toString())
                    comm.add(postID.toString())
                    comm.add(name)
                    comm.add(mail)
                    comm.add(body)
                }
                iter++
                comms.add(comm)
            } catch (e: FileNotFoundException) {
                break
            }
        }
        return comms
    }
}