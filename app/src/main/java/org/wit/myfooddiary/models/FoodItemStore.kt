package org.wit.myfooddiary.models

interface FoodItemStore {
    fun findAllUsers(): List<FoodModel>
    fun findOneUser(id: Long): FoodModel?
    fun createUser(foodItem: FoodModel)
    fun updateUser(foodItem: FoodModel)
    fun checkCredientials(foodItem: FoodModel): FoodModel?
    fun findAll(): List<FoodModel>
    fun create(foodItem: FoodModel)
    fun update(foodItem: FoodModel)
    fun findAllById(id: Long): List<FoodModel>
    fun delete(foodItem: FoodModel)
}