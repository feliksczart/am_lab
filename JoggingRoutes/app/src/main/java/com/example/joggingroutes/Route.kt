package com.example.joggingroutes

import android.util.Log
import com.google.android.gms.maps.model.LatLng


@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class Route(val name: String, val length: String, val route: MutableList<LatLng>) {

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

            val coords1 = getRouteCoords(rts[0])
            val coords2 = getRouteCoords(rts[1])
            val coords3 = getRouteCoords(rts[2])
            val coords4 = getRouteCoords(rts[3])
            val coords5 = getRouteCoords(rts[4])
            val coords6 = getRouteCoords(rts[5])
            val coords7 = getRouteCoords(rts[6])
            val coords8 = getRouteCoords(rts[7])
            val coords9 = getRouteCoords(rts[8])
            val coords10 = getRouteCoords(rts[9])

            return listOf(
                Route(
                    name1,
                    len1,
                    coords1
                ),
                Route(
                    name2,
                    len2,
                    coords2
                ),
                Route(
                    name3,
                    len3,
                    coords3
                ),
                Route(
                    name4,
                    len4,
                    coords4
                ),
                Route(
                    name5,
                    len5,
                    coords5
                ),
                Route(
                    name6,
                    len6,
                    coords6
                ),
                Route(
                    name7,
                    len7,
                    coords7
                ),
                Route(
                    name8,
                    len8,
                    coords8
                ),
                Route(
                    name9,
                    len9,
                    coords9
                ),
                Route(
                    name10,
                    len10,
                    coords10
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

        fun getRouteCoords(route: String): MutableList<LatLng> {
            val list = route.split("\n")
            val result = mutableListOf<LatLng>()
            for (i in list){
                if ("<trkpt" in i){
                    result.add(LatLng(
                        i.substringAfter("lat=\"").substringBefore('"').toDouble(),
                        i.substringAfter("lon=\"").substringBefore('"').toDouble()
                    ))
                }
            }
            return result
        }
    }

    @JvmName("getRoute1")
    fun getRouteCoords(): MutableList<LatLng> {
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