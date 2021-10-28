package org.wit.myfooddiary.models

import timber.log.Timber.i

var lastId = 0L
var lastUId = 0L

internal fun getUId(): Long {
    return lastUId++
}

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

    override fun findAllById(id: Long): List<FoodModel> {
        TODO("Not yet implemented")
    }

    override fun delete(foodItem: FoodModel) {
        TODO("Not yet implemented")
    }

    override fun findAllUsers(): List<FoodModel> {
        return foodItems
    }

    override fun createUser(foodItem: FoodModel) {
        foodItem.id = getUId()
        foodItems.add(foodItem)
        logAll()
    }

    override fun findOneUser(id: Long) : FoodModel? {
        var foundUser: FoodModel? = foodItems.find { p -> p.id == id }
        return foundUser
    }

    override fun updateUser(foodItem:FoodModel) {
        var foundUser: FoodModel? = foodItems.find { p -> p.id == foodItem.id }
        if (foundUser != null) {
            foundUser.firstName = foodItem.firstName
            foundUser.email = foodItem.email
            logAll()
        }
    }

    override fun checkCredientials(foodItem: FoodModel): FoodModel? {
        var foundUser: FoodModel? = foodItems.find { p ->
            p.password == foodItem.password

                &&
                p.email == foodItem.email
    }

    return foundUser
    }

    private fun logAll() {
        foodItems.forEach{ i("${it}") }
    }
}