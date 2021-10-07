package org.wit.myfooddiary.models

import timber.log.Timber.i

class FoodMemStore : FoodItemStore {
    val foodItems = ArrayList<FoodModel>()

    override fun findAll(): List<FoodModel> {
        return foodItems
    }

    override fun create(foodItem: FoodModel) {
        foodItems.add(foodItem)
        logAll()
    }

    fun logAll() {
        foodItems.forEach{ i("${it}") }
    }
}