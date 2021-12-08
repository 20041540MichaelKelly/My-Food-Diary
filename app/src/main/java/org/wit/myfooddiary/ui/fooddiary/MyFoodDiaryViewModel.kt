package org.wit.myfooddiary.ui.fooddiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel

class MyFoodDiaryViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addFoodItem(foodItem: FoodModel) {
        status.value = try {
            FoodManager.create(foodItem)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}