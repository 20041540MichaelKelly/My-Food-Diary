package org.wit.myfooddiary.models

import android.net.Uri

interface FoodItemStore {
    fun findAll(): List<FoodModel>
    fun create(foodItem: FoodModel)
    fun update(foodItem: FoodModel)
    fun findAllById(id: Long): List<FoodModel>
    fun deleteItem(foodItem: FoodModel)
    fun removeItem(foodItem: FoodModel)
    fun findAllBySearchValue(searchValue: String): List<FoodModel>?
}