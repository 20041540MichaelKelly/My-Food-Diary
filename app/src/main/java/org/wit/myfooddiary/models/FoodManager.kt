package org.wit.myfooddiary.models

import android.net.Uri
import java.util.ArrayList

object FoodManager : FoodItemStore {
    val foodItems = ArrayList<FoodModel>()


    override fun findAll(): List<FoodModel> {
        return foodItems
    }

    override fun create(foodItem: FoodModel, user: UserModel) {
        foodItem.id = generateRandomId()
        user.foodObject.add(foodItem )
        foodItems.add(foodItem)

    }

    override fun removeItem(foodItem: FoodModel) {
        foodItem.id = 0L
        foodItem.title = ""
        foodItem.description = ""
        foodItem.image = Uri.EMPTY
        foodItem.lat= 0.0
        foodItem.lng= 0.0
        foodItem.zoom = 0f
    }

    override fun deleteItem(foodItem: FoodModel) {
        foodItems.remove(foodItem)
    }

    override fun update(foodItem: FoodModel) {
        val foodItemList = findAll() as ArrayList<FoodModel>
        var foundFoundItem: FoodModel? = foodItemList.find { p -> p.id == foodItem.id }
        if (foundFoundItem != null) {
            foundFoundItem.fUid = foodItem.fUid
            foundFoundItem.title = foodItem.title
            foundFoundItem.description = foodItem.description
            foundFoundItem.amountOfCals = foodItem.amountOfCals
            foundFoundItem.image = foodItem.image
            foundFoundItem.lat = foodItem.lat
            foundFoundItem.lng = foodItem.lng
            foundFoundItem.zoom = foodItem.zoom
        }
    }

    override fun findAllBySearchValue(searchValue: String): List<FoodModel>? {
        val iList = ArrayList<FoodModel>()
        for (f in foodItems) {
            if (f.title == searchValue) {
                iList.add(f)
            }
        }
        return iList
    }

    override fun findAllById(id: Long): List<FoodModel> {
        val iList = ArrayList<FoodModel>()
        for(f in foodItems)
            if(f.fUid == id){
                iList.add(f)
            }
        return iList

    }
}