package org.wit.myfooddiary.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import org.wit.myfooddiary.api.FoodClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.collections.ArrayList

object FoodManager : FoodItemStore {
    val foodItems = ArrayList<FoodModel>()
//    override fun findAll(myFoodList: MutableLiveData<List<FoodModel>>) {
//        TODO("Not yet implemented")
//    }


//    override fun findAll(): List<FoodModel> {
//        return foodItems
//    }

    override fun findAll(myApiFoodList: MutableLiveData<List<FoodModel>>) {
        val call = FoodClient.getApi().getall()

        call.enqueue(object : Callback<List<FoodModel>> {
            override fun onResponse(call: Call<List<FoodModel>>, response: Response <List<FoodModel>>) {
                myApiFoodList.value = response.body() as List<FoodModel>
                    Timber.i("Retrofit JSON = ${response.body()}"
                )
            }

            override fun onFailure(call: Call<List<FoodModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message $call.data")
            }

        })
    }

    override fun findAllByFilter(numberOfItems: String, maxCals:String, myApiFoodList: MutableLiveData<List<FoodModel>>) {
        val call = FoodClient.getApi().getallbydescription(maxCals,numberOfItems)

        call?.enqueue(object : Callback<List<FoodModel>> {
            override fun onResponse(call: Call<List<FoodModel>>, response: Response <List<FoodModel>>) {
                myApiFoodList.value = response.body() as List<FoodModel>
                Timber.i("Retrofit JSON = ${response.body()}"
                )
            }

            override fun onFailure(call: Call<List<FoodModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message $call.data")
            }

        })
    }

    override fun findAllByUid(
        userid: String,
        myFoodList: MutableLiveData<List<FoodModel>>
    ) {
        TODO("Not yet implemented")
    }

    override fun findById(foodid: String, fooditem: MutableLiveData<FoodModel>) {
        TODO("Not yet implemented")
    }

    override fun findCoordinatesByUid(
        userid: String,
        lat: MutableLiveData<List<Double>>,
        lng: MutableLiveData<List<Double>>
    ) {
        TODO("Not yet implemented")
    }


    fun findCoordinatesByUid(
        userid: String,
        foodid: String,
        myFoodList: MutableLiveData<List<FoodModel>>
    ) {
        TODO("Not yet implemented")
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, fooditem: FoodModel) {
        TODO("Not yet implemented")
    }

    override fun delete(userid: String, foodid: String) {
        TODO("Not yet implemented")
    }

    override fun update(
        firebaseUser: MutableLiveData<FirebaseUser>,
        foodid: String,
        fooditem: FoodModel
    ) {
        TODO("Not yet implemented")
    }


//    override fun create(foodItem: FoodModel) {
//
//        val call = FoodClient.getApi().create(foodItem)
//
//        call.enqueue(object : Callback<FoodWraper> {
//            override fun onResponse(call: Call<FoodWraper>,
//                                    response: Response<FoodWraper>
//            ) {
//                val foodWrapper = response.body()
//                if (foodWrapper != null) {
//                    Timber.i("Retrofit ${foodWrapper.message}")
//                    Timber.i("Retrofit ${foodWrapper.data.toString()}")
//                }
//            }
//
//            override fun onFailure(call: Call<FoodWraper>, t: Throwable) {
//                Timber.i("Retrofit Error : $t.message")
//            }
//        })
//    }
//
////    override fun create(foodItem: FoodModel, user: UserModel) {
////        foodItem.id = generateRandomId()
////        user.foodObject.add(foodItem )
////        foodItems.add(foodItem)
////
////    }
//
////        override fun create(foodItem: FoodModel) {
////        foodItem.id = generateRandomId()
////        foodItems.add(foodItem)
////   }
//
//    override fun update(foodItem: FoodModel) {
//        TODO("Not yet implemented")
//    }
//
//    override fun removeItem(foodItem: FoodModel) {
////        foodItem.id = 0L
////        foodItem.title = ""
////        foodItem.description = ""
////        foodItem.image = Uri.EMPTY
////        foodItem.lat= 0.0
////        foodItem.lng= 0.0
////        foodItem.zoom = 0f
//    }
//
//    override fun deleteItem(foodItem: FoodModel) {
////        foodItems.remove(foodItem)
//    }
//
////    override fun update(foodItem: FoodModel) {
////        val foodItemList = findAll() as ArrayList<FoodModel>
////        var foundFoundItem: FoodModel? = foodItemList.find { p -> p.id == foodItem.id }
////        if (foundFoundItem != null) {
////            foundFoundItem.fUid = foodItem.fUid
////            foundFoundItem.title = foodItem.title
////            foundFoundItem.description = foodItem.description
////            foundFoundItem.amountOfCals = foodItem.amountOfCals
////            foundFoundItem.image = foodItem.image
////            foundFoundItem.lat = foodItem.lat
////            foundFoundItem.lng = foodItem.lng
////            foundFoundItem.zoom = foodItem.zoom
////        }
////    }
//
//    override fun findAllBySearchValue(searchValue: String): List<FoodModel>? {
//        val iList = ArrayList<FoodModel>()
//        for (f in foodItems) {
//            if (f.title == searchValue) {
//                iList.add(f)
//            }
//        }
//        return iList
//    }
//
//    override fun findAllById(id: Long): List<FoodModel> {
//        val iList = ArrayList<FoodModel>()
//        for(f in foodItems)
//            if(f.fUid == id){
//                iList.add(f)
//            }
//        return iList
//
//    }
//
//    override fun findById(id: Long): FoodModel? {
//        val foundFoodItem: FoodModel? = foodItems.find { p -> p.id == id }
//        return foundFoodItem
//
//    }
}