package com.example.cocktailmenu

class Cocktail(
    var name: String,
    var details: String,
    var alcoholic: String,
    var recipe: String,
    var imageId: Int
) {

    @JvmName("getRecipe1")
    fun getRecipe(): String {
        return recipe
    }

    @JvmName("getName1")
    fun getName(): String {
        return name
    }

    @JvmName("getDetails1")
    fun getDetails(): String {
        return details
    }

    @JvmName("getAlco1")
    fun getAlco(): String {
        return alcoholic
    }

    @JvmName("getImageId1")
    fun getImageResourceId(): Int {
        return imageId
    }

    override fun toString(): String {
        return name
    }
}

