package org.wit.myfooddiary.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.auth.FirebaseUser
import org.wit.myfooddiary.firebase.FirebaseDBManager
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

class FoodLocationViewModel: ViewModel() {

    private val myFoodList = MutableLiveData<List<FoodModel>>()
    private val foodItem = MutableLiveData<FoodModel>()


    val observableFoodLocationList: LiveData<List<FoodModel>>
        get() = myFoodList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            val userid = liveFirebaseUser.value!!.uid
            val foodid = foodItem.value!!.fid
            if (foodid != null) {
                FirebaseDBManager.findCoordinatesByUid(userid, foodid, myFoodList)
            }
            Timber.i("Retrofit Success : ${myFoodList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }
}