package org.wit.myfooddiary.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

//interface FoodItemStore {
//    fun findAll(myFoodList: MutableLiveData<List<FoodModel>>)
//    fun create(foodItem: FoodModel)
//    fun update(foodItem: FoodModel)
//    fun findAllById(id: Long): List<FoodModel>
//    fun deleteItem(foodItem: FoodModel)
//    fun removeItem(foodItem: FoodModel)
//    fun findAllBySearchValue(searchValue: String): List<FoodModel>?
//    fun findById(id: Long): FoodModel?
//}

interface FoodItemStore {
    fun findAll(myApiFoodList:
                MutableLiveData<List<FoodModel>>)
    fun findAllByUid(
        userid: String,
        myFoodList:
        MutableLiveData<List<FoodModel>>
    )
    fun findById(foodid: String,
                 fooditem: MutableLiveData<FoodModel>)
    fun findCoordinatesByUid(userid: String, lat: MutableLiveData<List<Double>>, lng: MutableLiveData<List<Double>>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, fooditem: FoodModel)
    fun update(
        firebaseUser: MutableLiveData<FirebaseUser>,
        foodid: String,
        fooditem: FoodModel
    )
    fun findAllByFilter(numberOfItems: String, maxCals:String, myApiFoodList: MutableLiveData<List<FoodModel>>)

    fun delete(
        firebaseUser: MutableLiveData<FirebaseUser>,
        fooditem: FoodModel,
        myFoodList: MutableLiveData<List<FoodModel>>
    )
}