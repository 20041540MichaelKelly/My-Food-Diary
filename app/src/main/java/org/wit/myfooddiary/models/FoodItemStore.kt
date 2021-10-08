package org.wit.myfooddiary.models

interface FoodItemStore {
    fun findAll(): List<FoodModel>
    fun create(foodItem: FoodModel)
    fun update(foodItem: FoodModel)
}