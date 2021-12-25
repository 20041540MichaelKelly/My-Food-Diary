package org.wit.myfooddiary.ui.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.myfooddiary.firebase.FirebaseDBManager
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

class MyFoodListViewModel : ViewModel() {

    private val myFoodList = MutableLiveData<List<FoodModel>>()
    private val lat = MutableLiveData<List<Double>>()
    private val lng = MutableLiveData<List<Double>>()


    val observableFoodItemsList: LiveData<List<FoodModel>>
        get() = myFoodList

    val observableLatList: LiveData<List<Double>>
        get() = lat

   var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            val userid = liveFirebaseUser.value!!.uid
            FirebaseDBManager.findAllByUid(userid, myFoodList)
            Timber.i("Retrofit Success : ${myFoodList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun getCordinates(){
        val userid = liveFirebaseUser.value!!.uid
        FirebaseDBManager.findCoordinatesByUid(userid, lat, lng)

    }







}