package org.wit.myfooddiary.main

import android.app.Application
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val foodItems = ArrayList<FoodModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Food started")
    }
}