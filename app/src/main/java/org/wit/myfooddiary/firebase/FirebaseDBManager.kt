package org.wit.myfooddiary.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.myfooddiary.models.FoodItemStore
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object FirebaseDBManager  : FoodItemStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(myFoodList: MutableLiveData<List<FoodModel>>) {
        database.child("food")
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
                    database.child("food")
                        .removeEventListener(this)

                    myFoodList.value = localList
                }
            })
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

    override fun findById(foodid: String, fooditem: MutableLiveData<FoodModel>) {
        database.child("food").child(foodid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Food error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    var fItem = FoodModel()
//
                    val children = snapshot.children
                    children.forEach {
                        val foodItem = it.getValue(FoodModel::class.java)
                        if(foodItem != null) {
                            fItem = foodItem
                        }
                    }
                    database.child("food").child(foodid)
                        .removeEventListener(this)
                    if(fItem.fid != "") {
                        fooditem.value = fItem
                    }
                }
            })
    }

    override fun findCoordinatesByUid(userid: String, lat: MutableLiveData<List<Double>>, lng: MutableLiveData<List<Double>>) {
        database.child("user-food").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Food error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children



                }

            })


     }


    override fun delete(
        firebaseUser: MutableLiveData<FirebaseUser>,
        fooditem: FoodModel,
        myFoodList: MutableLiveData<List<FoodModel>>
    ) {
        database.child("user-food").child(firebaseUser.value!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Food error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    var fItem: String? = null
                    val uid = firebaseUser.value!!.uid
                    val localList = ArrayList<FoodModel>()

                    children.forEach {
                        val foodItem = it.getValue(FoodModel::class.java)
                        if (foodItem?.fid == fooditem.fid) {
                            if (foodItem != null) {
                                fItem = foodItem.fid.toString()
                            }

                        }
                        database.child("user-food/$uid/$fItem").removeValue()
                        database.child("food/$fItem").removeValue()
//                        if (foodItem != null) {
//                            localList.add(foodItem)
//                        }
//                    }
//                    database.child("user-food").child(firebaseUser.value!!.uid)
//                        .removeEventListener(this)
//
//                    myFoodList.value = localList
                        //}
                    }
                }

            })
    }

    override fun update(
        firebaseUser: MutableLiveData<FirebaseUser>,
        foodid: String,
        fooditem: FoodModel
    ) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        fooditem.fid = foodid
        fooditem.uid = uid
        val fooditemValues = fooditem.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/food/$foodid"] = fooditemValues
        childAdd["/user-food/$uid/$foodid"] = fooditemValues

        database.updateChildren(childAdd)
    }

    override fun findAllByFilter(
        numberOfItems: String,
        maxCals: String,
        myApiFoodList: MutableLiveData<List<FoodModel>>
    ) {
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