package org.wit.myfooddiary.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.myfooddiary.helpers.*
import java.util.*

val JSON_FILE = "fooditems.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<FoodModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FoodItemJSONStore : FoodItemStore, AnkoLogger {

    val context: Context
    var foodItems = mutableListOf<FoodModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FoodModel> {
        return foodItems
    }

    override fun create(foodItem: FoodModel) {
        foodItem.id = generateRandomId()
        foodItems.add(foodItem)
        serialize()
    }


    override fun update(foodItem: FoodModel) {
        // todo
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(foodItems, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        foodItems = Gson().fromJson(jsonString, listType)
    }
}