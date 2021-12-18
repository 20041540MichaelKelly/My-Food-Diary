package org.wit.myfooddiary.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.myfooddiary.models.FoodItemStore
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

object FirebaseDBManager  : FoodItemStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(myFoodList: MutableLiveData<List<FoodModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAllByUid(userid: String, myFoodList: MutableLiveData<List<FoodModel>>) {
        database.child("user-food").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Food error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<FoodModel>()
                    val children = snapshot.children
                    children.forEach {
                        val foodItem = it.getValue(FoodModel::class.java)
                        localList.add(foodItem!!)
                    }
                    database.child("user-food").child(userid)
                        .removeEventListener(this)

                    myFoodList.value = localList
                }
            })
    }

    override fun findById(userid: String, foodid: String, fooditem: MutableLiveData<FoodModel>) {
        TODO("Not yet implemented")
    }



    override fun delete(userid: String, foodid: String) {
        TODO("Not yet implemented")
    }

    override fun update(userid: String, foodid: String, fooditem: FoodModel) {
        TODO("Not yet implemented")
    }


    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, fooditem: FoodModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("food").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        fooditem.fid = key
        fooditem.uid = uid
        val fooditemValues = fooditem.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/food/$key"] = fooditemValues
        childAdd["/user-food/$uid/$key"] = fooditemValues

        database.updateChildren(childAdd)
    }
}