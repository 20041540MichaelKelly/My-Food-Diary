package org.wit.myfooddiary.main

import android.app.Application
import org.wit.myfooddiary.models.FoodMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

   // val foodItems = ArrayList<FoodModel>()
    val foodItems = FoodMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")

    }
}