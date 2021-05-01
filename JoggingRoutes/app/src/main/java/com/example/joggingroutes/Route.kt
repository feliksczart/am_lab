package com.example.joggingroutes

import android.util.Log


@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class Route(val name: String, val length: String, val route: String) {

    companion object {
        val routes = getData() //listOf(

        private fun getData(): List<Route> {
            val rts = LoadingActivity.data

            val name1 = getRouteName(rts[0])
            val name2 = getRouteName(rts[1])
            val name3 = getRouteName(rts[2])
            val name4 = getRouteName(rts[3])
            val name5 = getRouteName(rts[4])
            val name6 = getRouteName(rts[5])
            val name7 = getRouteName(rts[6])
            val name8 = getRouteName(rts[7])
            val name9 = getRouteName(rts[8])
            val name10 = getRouteName(rts[9])

            val len1 = getRouteLength(rts[0])
            val len2 = getRouteLength(rts[1])
            val len3 = getRouteLength(rts[2])
            val len4 = getRouteLength(rts[3])
            val len5 = getRouteLength(rts[4])
            val len6 = getRouteLength(rts[5])
            val len7 = getRouteLength(rts[6])
            val len8 = getRouteLength(rts[7])
            val len9 = getRouteLength(rts[8])
            val len10 = getRouteLength(rts[9])

            return listOf(
                Route(
                    name1,
                    len1,
                    "len"
                ),
                Route(
                    name2,
                    len2,
                    "len"
                ),
                Route(
                    name3,
                    len3,
                    "len"
                ),
                Route(
                    name4,
                    len4,
                    "len"
                ),
                Route(
                    name5,
                    len5,
                    "len"
                ),
                Route(
                    name6,
                    len6,
                    "len"
                ),
                Route(
                    name7,
                    len7,
                    "len"
                ),
                Route(
                    name8,
                    len8,
                    "len"
                ),
                Route(
                    name9,
                    len9,
                    "len"
                ),
                Route(
                    name10,
                    len10,
                    "len"
                )
            )
        }

        fun getRouteName(route: String): String {
            val list = route.split("\n")
            var result = ""
            for (i in list){
                if ("<name" in i){
                    result = i.substringAfter('"').substringBefore('"')
                    Log.i("s",result)
                    break
                }
            }
            return result
        }

        fun getRouteLength(route: String): String {
            val list = route.split("\n")
            var result = ""
            for (i in list){
                if ("<length" in i){
                    result = i.substringAfter('"').substringBefore('"')
                    Log.i("s",result)
                    break
                }
            }
            return result
        }
    }

    @JvmName("getRoute1")
    fun getRoute(): String {
        return this.route
    }

    @JvmName("getName1")
    fun getName(): String {
        return this.name
    }

    @JvmName("getLength1")
    fun getLength(): String {
        return this.length
    }

    override fun toString(): String {
        return this.name
    }
}