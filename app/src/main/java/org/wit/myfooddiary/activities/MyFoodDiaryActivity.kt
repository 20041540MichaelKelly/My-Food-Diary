package org.wit.myfooddiary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.myfooddiary.databinding.ActivityMyfooddiaryBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber.i

class MyFoodDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyfooddiaryBinding
    var foodItem = FoodModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyfooddiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Placemark Activity started...")
        binding.btnAdd.setOnClickListener() {
            foodItem.title = binding.foodTitle.text.toString()
            foodItem.description = binding.description.text.toString()
            if (foodItem.title.isNotEmpty()) {
                app.foodItems.add(foodItem.copy())
                i("add Button Pressed: ${foodItem}")
                for (i in app.foodItems.indices)
                { i("FoodItem[$i]:${this.app.foodItems[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}