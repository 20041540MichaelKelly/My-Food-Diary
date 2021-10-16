package org.wit.myfooddiary.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class FoodMemStore : FoodItemStore {
    val foodItems = ArrayList<FoodModel>()

    override fun findAll(): List<FoodModel> {
        return foodItems
    }

    override fun create(foodItem: FoodModel) {
        foodItem.id = getId()
        foodItems.add(foodItem)
        logAll()
    }

    override fun update(foodItem: FoodModel) {
        var foundFoodItem: FoodModel? = foodItems.find { p -> p.id == foodItem.id }
        if (foundFoodItem != null) {
            foundFoodItem.title = foodItem.title
            foundFoodItem.description = foodItem.description
            foundFoodItem.image = foodItem.image
            foundFoodItem.lat = foodItem.lat
            foundFoodItem.lng = foodItem.lng
            foundFoodItem.zoom = foodItem.zoom
            logAll()
        }
    }

    private fun logAll() {
        foodItems.forEach{ i("${it}") }
    }
}