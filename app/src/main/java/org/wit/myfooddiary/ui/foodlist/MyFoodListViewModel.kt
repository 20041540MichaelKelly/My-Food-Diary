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

   var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    init {
        load()
    }

    fun load() {
        try {
            readOnly.value = false
            val userid = liveFirebaseUser.value!!.uid
            FirebaseDBManager.findAllByUid(userid, myFoodList)
            Timber.i("Retrofit Success : ${myFoodList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(myFoodList)
            Timber.i("Report LoadAll Success : ${myFoodList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

    fun getCordinates(){
        val userid = liveFirebaseUser.value!!.uid
        FirebaseDBManager.findCoordinatesByUid(userid, lat, lng)

    }

    fun updateFoodItem(firebaseUser: MutableLiveData<FirebaseUser>, foodid: String, foodItem: FoodModel) {
        try {
            FirebaseDBManager.update(firebaseUser, foodid, foodItem)

        } catch (e: IllegalArgumentException) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun deleteItem(firebaseUser: MutableLiveData<FirebaseUser>,foodItem: FoodModel){
        try
        {
            FirebaseDBManager.delete(firebaseUser, foodItem, myFoodList)
            Timber.i("deleted : ${foodItem.fid}")

        } catch (e: IllegalArgumentException) {
            Timber.i("Retrofit Error : $e.message")
        }
    }







}