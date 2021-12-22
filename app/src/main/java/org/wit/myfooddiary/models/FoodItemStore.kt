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
    fun findById(userid:String, foodid: String,
                 fooditem: MutableLiveData<FoodModel>)
    fun findCoordinatesByUid(userid:String, foodid: String,
                             myFoodList: MutableLiveData<List<FoodModel>>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, fooditem: FoodModel)
    fun delete(userid:String, foodid: String)
    fun update(userid:String, foodid: String, fooditem: FoodModel)
}