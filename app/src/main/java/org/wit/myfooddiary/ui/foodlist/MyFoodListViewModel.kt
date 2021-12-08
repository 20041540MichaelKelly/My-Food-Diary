package org.wit.myfooddiary.ui.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel

class MyFoodListViewModel : ViewModel() {

    private val myFoodList = MutableLiveData<List<FoodModel>>()

    val observableFoodItemsList: LiveData<List<FoodModel>>
        get() = myFoodList

    init {
        load()
    }

    fun load() {
        myFoodList.value = FoodManager.findAll()
    }
}