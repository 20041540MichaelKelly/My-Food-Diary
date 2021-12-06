package org.wit.myfooddiary.ui.fooddiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel

class MyFoodDiaryViewModel : ViewModel() {

    private val myFoodList = MutableLiveData<List<FoodModel>>()

    val observableDonationsList: LiveData<List<FoodModel>>
        get() = myFoodList

    init {
        load()
    }

    fun load() {
        myFoodList.value = FoodManager.findAll()
    }
}