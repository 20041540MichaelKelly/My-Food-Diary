package org.wit.myfooddiary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityMyfooddiaryBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber.i

class MyFoodDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyfooddiaryBinding
    var foodItem = FoodModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyfooddiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.btnAdd.setOnClickListener() {
            foodItem.title = binding.foodTitle.text.toString()
            foodItem.description = binding.description.text.toString()
            if (foodItem.title.isNotEmpty()) {
                app.foodItems.create(foodItem.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_fooditem, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

