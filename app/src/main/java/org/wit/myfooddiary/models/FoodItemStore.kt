package org.wit.myfooddiary.models

interface FoodItemStore {
    fun findAll(): List<FoodModel>
    fun create(foodItem: FoodModel, user: UserModel)
    fun update(foodItem: FoodModel)
    fun findAllById(id: Long): List<FoodModel>
    fun deleteItem(foodItem: FoodModel)
    fun removeItem(foodItem: FoodModel)
    fun findAllBySearchValue(searchValue: String): List<FoodModel>?
}