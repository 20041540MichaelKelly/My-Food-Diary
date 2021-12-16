package org.wit.myfooddiary.ui.fooddiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.myfooddiary.firebase.FirebaseDBManager
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel

class MyFoodDiaryViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addFoodItem(firebaseUser: MutableLiveData<FirebaseUser>,
                    foodItem: FoodModel) {
        status.value = try {
            FirebaseDBManager.create(firebaseUser,foodItem)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}