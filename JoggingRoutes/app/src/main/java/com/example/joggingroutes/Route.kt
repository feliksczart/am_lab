package com.example.joggingroutes

class Route(val name: String, val route: String) {

    companion object {
        val routes = listOf(
            Route(
                "Bloody Mary",
                "X"
            ),
            Route(
                "Bloody Mary",
                "Składniki: \n 40 ml wódki \n 10 ml soku z cytryny \n 120 ml soku pomidorowego \n sos worchestershire \n sól \n pieprz \n tabasco \n gałązka selera naciowego \n\n Sposób przygotowania: \n Wszystkie składniki wymieszać w szklance z lodem i ozdobić selerem naciowym."
            ),
            Route(
                "Bloody Mary",
                "Składniki: \n 40 ml wódki \n 10 ml soku z cytryny \n 120 ml soku pomidorowego \n sos worchestershire \n sól \n pieprz \n tabasco \n gałązka selera naciowego \n\n Sposób przygotowania: \n Wszystkie składniki wymieszać w szklance z lodem i ozdobić selerem naciowym."
            )
        )
    }

    @JvmName("getRoute1")
    fun getRoute(): String {
        return this.route
    }

    @JvmName("getName1")
    fun getName(): String {
        return this.name
    }

    override fun toString(): String {
        return this.name
    }


}