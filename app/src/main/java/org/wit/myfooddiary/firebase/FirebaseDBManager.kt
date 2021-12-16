package org.wit.myfooddiary.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.wit.myfooddiary.models.FoodItemStore
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

object FirebaseDBManager  : FoodItemStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(myFoodList: MutableLiveData<List<FoodModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, myFoodList: MutableLiveData<List<FoodModel>>) {
        TODO("Not yet implemented")
    }

    override fun findById(userid: String, foodid: String, fooditem: MutableLiveData<FoodModel>) {
        TODO("Not yet implemented")
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, fooditem: FoodModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("donations").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        fooditem.uid = key
        val fooditemValues = fooditem.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/food/$key"] = fooditemValues
        childAdd["/user-food/$uid/$key"] = fooditemValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, foodid: String) {
        TODO("Not yet implemented")
    }

    override fun update(userid: String, foodid: String, fooditem: FoodModel) {
        TODO("Not yet implemented")
    }
}