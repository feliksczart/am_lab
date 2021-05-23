package com.example.cocktailmenu

class Cocktail(private val name: String, private val recipe: String) {
    companion object {
        val cocktails = listOf(
            Cocktail(
                "Bloody Mary",
                "Składniki: \n 40 ml wódki \n 10 ml soku z cytryny \n 120 ml soku pomidorowego \n sos worchestershire \n sól \n pieprz \n tabasco\n gałązka selera naciowego \n\n Sposób przygotowania: \n Wszystkie składnikiwymieszać w szklance z lodem i ozdobić selerem naciowym."
            ),
            Cocktail(
                "Bloody Mary",
                "Składniki: \n 40 ml wódki \n 10 ml soku z cytryny \n 120 ml soku pomidorowego \n sos worchestershire \n sól \n pieprz \n tabasco\n gałązka selera naciowego \n\n Sposób przygotowania: \n Wszystkie składnikiwymieszać w szklance z lodem i ozdobić selerem naciowym."
            ),
            Cocktail(
                "Bloody Mary",
                "Składniki: \n 40 ml wódki \n 10 ml soku z cytryny \n 120 ml soku pomidorowego \n sos worchestershire \n sól \n pieprz \n tabasco\n gałązka selera naciowego \n\n Sposób przygotowania: \n Wszystkie składnikiwymieszać w szklance z lodem i ozdobić selerem naciowym."
            )
        )
    }

    @JvmName("getRecipe1")
    fun getRecipe(): String {
        return recipe
    }

    @JvmName("getName1")
    fun getName(): String {
        return name
    }

    override fun toString(): String {
        return name
    }
}