package com.example.cocktailmenu


import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class DrinksGetter(private val activity: LauncherActivity) :
    AsyncTask<Void, Void, ArrayList<Cocktail>>() {

    companion object{
        val cocktails = ArrayList<Cocktail>()
    }

    override fun doInBackground(vararg params: Void?): ArrayList<Cocktail>? {
        val drinks: ArrayList<Cocktail> = arrayListOf()
        for (i in 0..1){
            val url: String = if (i == 0){
                "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Ordinary_Drink"
            } else{
                "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Cocktail"
            }
            val connection = URL(url)
            val httpURLConnection = connection.openConnection() as HttpURLConnection
            httpURLConnection.connectTimeout = 3000
            httpURLConnection.connect()
            if (httpURLConnection.responseCode == 200) {
                val text = connection.readText()
                if (text.compareTo("") == 0) {
                    return null
                }
                drinks += getDrinksFromResponse(text)
            } else {
                return null
            }
        }
        return drinks
    }

    override fun onPostExecute(result: ArrayList<Cocktail>?) {
        if (result != null) {
            activity.openMainActivity()
        }
        super.onPostExecute(result)
    }


    private fun getDrinksFromResponse(response: String): ArrayList<Cocktail> {
        val cocktailNames = ArrayList<String>()
        val jsonObject = JSONObject(response)
        val cocktailList = jsonObject.get("drinks") as JSONArray
        for (i in 0 until 10) {
            val json = cocktailList[i] as JSONObject

            val name = json.getString("strDrink")
            cocktailNames.add(name)
        }

        for (i in cocktailNames) {
            getDrinksBackground(i)?.let { cocktails.add(it) }
        }
        return cocktails
    }

    private fun getDrinksBackground(name: String): Cocktail? {
        val encode = name.replace(" ", "%20")
        val url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=$encode"
        val connection = URL(url)
        val httpURLConnection = connection.openConnection() as HttpURLConnection
        httpURLConnection.connectTimeout = 3000
        httpURLConnection.connect()
        if (httpURLConnection.responseCode == 200) {
            val text = connection.readText()
            if (text.compareTo("") == 0) {
                return null
            }
            return getDrinkByName(text, name)
        } else {
            return null
        }
    }

    private fun getDrinkByName(response: String, name: String): Cocktail {
        val jsonObject = JSONObject(response)
        val cocktailDetails = jsonObject.get("drinks") as JSONArray

        return Cocktail(
            name,
            (cocktailDetails[0] as JSONObject).getString("strInstructionsDE"),
            (cocktailDetails[0] as JSONObject).getString("strAlcoholic"),
            (cocktailDetails[0] as JSONObject).getString("strInstructions")
        )
    }

}