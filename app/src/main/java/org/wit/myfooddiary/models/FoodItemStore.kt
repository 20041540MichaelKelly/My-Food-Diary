package org.wit.myfooddiary.models

import android.net.Uri
import androidx.lifecycle.MutableLiveData

interface FoodItemStore {
    fun findAll(myFoodList: MutableLiveData<List<FoodModel>>)
    fun create(foodItem: FoodModel)
    fun update(foodItem: FoodModel)
    fun findAllById(id: Long): List<FoodModel>
    fun deleteItem(foodItem: FoodModel)
    fun removeItem(foodItem: FoodModel)
    fun findAllBySearchValue(searchValue: String): List<FoodModel>?
    fun findById(id: Long): FoodModel?
}