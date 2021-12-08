package org.wit.myfooddiary.ui.individualfooditem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel

class IndividualFoodItemViewModel : ViewModel() {
    private val foodItem = MutableLiveData<FoodModel>()

    val observableIndividualFoodItem: LiveData<FoodModel>
        get() = foodItem

    fun getFoodItem(id: Long) {
        foodItem.value = FoodManager.findById(id)
    }
}