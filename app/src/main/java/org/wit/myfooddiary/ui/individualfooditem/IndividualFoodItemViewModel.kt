package org.wit.myfooddiary.ui.individualfooditem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.myfooddiary.firebase.FirebaseDBManager
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

class IndividualFoodItemViewModel : ViewModel() {
    private val foodItem = MutableLiveData<FoodModel>()

    val observableIndividualFoodItem: LiveData<FoodModel>
        get() = foodItem

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()


    fun getFoodItem(foodid: String) {
        FirebaseDBManager.findById(foodid, foodItem)
        Timber.i("Retrofit Success : ${foodItem.value.toString()}")

    }
}