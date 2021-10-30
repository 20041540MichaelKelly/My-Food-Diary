package org.wit.myfooddiary.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.myfooddiary.models.*
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

   // val foodItems = ArrayList<FoodModel>()
   // val foodItems = FoodMemStore()
   lateinit var foodItems: FoodItemStore
   lateinit var users: UserStore
  // lateinit var users: UserStore

    //val foodItems = FoodItemJSONStore()
    //val users = UserMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        foodItems = FoodItemJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)

        // users = UserJSONStore(applicationContext)
        i("Placemark started")

    }
}