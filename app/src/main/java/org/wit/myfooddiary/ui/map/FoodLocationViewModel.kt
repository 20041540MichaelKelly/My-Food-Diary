package org.wit.myfooddiary.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.auth.FirebaseUser
import org.wit.myfooddiary.firebase.FirebaseDBManager
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

class FoodLocationViewModel: ViewModel() {

    private val foodItem = MutableLiveData<FoodModel>()

    val observableIndividualFoodItem: LiveData<FoodModel>
        get() = foodItem

//    fun getFoodItem(userid: String, foodid: String, foodItem: MutableLiveData<FoodModel>) {
//        foodItem.value = FirebaseDBManager.findById(userid,foodid, foodItem)
//    }
}