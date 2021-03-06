@file:Suppress("DEPRECATION")

package com.example.joggingroutes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.joggingroutes.activity.DetailActivity
import com.example.joggingroutes.activity.MainActivity
import com.example.joggingroutes.activity.StatsActivity
import com.example.joggingroutes.db.HttpsTrustManager
import com.example.joggingroutes.fragment.RouteDetailFragment
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


@Suppress("DEPRECATION")
class DBHelper(ctx: Context) : AsyncTask<String?, Void?, String?>() {
    @SuppressLint("StaticFieldLeak")
    var ctx: Context = ctx
    lateinit var method: String
    lateinit var username: String
    override fun doInBackground(vararg params: String?): String? {
        method = params[0].toString()
        if (method == "register") {
            val name = params[1]
            val password = params[2]
            val reg_url = "https://192.168.0.19/phpJogging/register.php"
            try {
                val url = URL(reg_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("name", "UTF-8").toString() + "=" + URLEncoder.encode(
                        name, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
                        password, "UTF-8"
                    )

                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                os.close()
                val `is`: InputStream = httpURLConnection.inputStream
                `is`.close()
                return "Registration success"
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (method == "login") {
            val name = params[1]
            username = name.toString()
            val password = params[2]
            lateinit var response: String
            val log_url = "https://192.168.0.19/phpJogging/login.php"
            try {
                val url = URL(log_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("name", "UTF-8").toString() + "=" + URLEncoder.encode(
                        name, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(
                        password, "UTF-8"
                    )

                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                os.close()

                val `is`: InputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(`is`))

                try {
                    while ((bufferedReader.readLine().also { response = it }) != null) {
                    }
                } catch (e: Exception) {
                }

                bufferedReader.close()
                `is`.close()
                httpURLConnection.disconnect()
                return response
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else if (method == "insert result") {
            val name = params[1]
            val route = params[2]
            val result = params[3]
            val date = params[4]
            val res_url = "https://192.168.0.19/phpJogging/insertresult.php"
            try {
                val url = URL(res_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("name", "UTF-8").toString() + "=" + URLEncoder.encode(
                        name, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("route", "UTF-8")
                                .toString() + "=" + URLEncoder.encode(
                        route, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("result", "UTF-8")
                                .toString() + "=" + URLEncoder.encode(
                        result, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("date", "UTF-8").toString() + "=" + URLEncoder.encode(
                        date, "UTF-8"
                    )

                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                os.close()
                val `is`: InputStream = httpURLConnection.inputStream
                `is`.close()
                return "Success"
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (method == "get results") {
            val name = params[1]
            val route = params[2]
            lateinit var response: String
            val log_url = "https://192.168.0.19/phpJogging/getresults.php"
            try {
                val url = URL(log_url)
                HttpsTrustManager.allowAllSSL()
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val os: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                val data: String =
                    URLEncoder.encode("name", "UTF-8").toString() + "=" + URLEncoder.encode(
                        name, "UTF-8"
                    ) + "&" +
                            URLEncoder.encode("route", "UTF-8") + "=" + URLEncoder.encode(
                        route, "UTF-8"
                    )

                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                os.close()

                val `is`: InputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(`is`))

                try {
                    while ((bufferedReader.readLine().also { response = it }) != null) {
                    }
                } catch (e: Exception) {
                }
                Log.i("aa",response)
                bufferedReader.close()
                `is`.close()
                httpURLConnection.disconnect()
                return response
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        if (method == "login" && result == "Success") {
            Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(ctx, MainActivity::class.java)
            intent.putExtra("username", username)
            ctx.startActivity(intent)
            //(ctx as Activity).finish()
        } else if (method == "login" && result == "Error") {
            Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show()
        } else if (method == "register") {
            Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
        } else if (method == "get results") {
            val intent = Intent(ctx, StatsActivity::class.java)
            intent.putExtra("result", result)
            ctx.startActivity(intent)
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onProgressUpdate(vararg values: Void?) {
        super.onProgressUpdate(*values)
    }
}