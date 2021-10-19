package org.wit.myfooddiary.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.myfooddiary.models.FoodItemStore
import org.wit.myfooddiary.models.FoodMemStore
import org.wit.myfooddiary.models.UserMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

   // val foodItems = ArrayList<FoodModel>()
    val foodItems = FoodMemStore()
   //lateinit var foodItems: FoodItemStore

    //val foodItems = FoodItemJSONStore()
    val users = UserMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
       // foodItems = FoodItemJSONStore(applicationContext)
        i("Placemark started")

    }
}