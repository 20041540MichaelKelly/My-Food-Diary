package org.wit.myfooddiary.ui.apifoodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.myfooddiary.firebase.FirebaseDBManager
import org.wit.myfooddiary.models.FoodManager
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

class ApiFoodListViewModel : ViewModel() {

    private val myApiFoodList = MutableLiveData<List<FoodModel>>()

    val observableApiFoodItemsList: LiveData<List<FoodModel>>
        get() = myApiFoodList

    init {
        load()
    }

    fun load() {
        try {
            FoodManager.findAll(myApiFoodList)
            Timber.i("Retrofit Success : $myApiFoodList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun filterApi(amtOfItems: String, seekBarAmt:String) {
        try {
            FoodManager.findAllByFilter(amtOfItems, seekBarAmt,myApiFoodList)
            Timber.i("Retrofit Success : $myApiFoodList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun update(firebaseUser: MutableLiveData<FirebaseUser>,
                    foodItem: FoodModel) {
        try {
            FirebaseDBManager.create(firebaseUser,foodItem)

        } catch (e: IllegalArgumentException) {

        }
    }
}