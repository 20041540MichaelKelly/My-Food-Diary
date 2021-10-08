package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityMyfooddiaryBinding
import org.wit.myfooddiary.helpers.showImagePicker
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber.i

class MyFoodDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyfooddiaryBinding
    var foodItem = FoodModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityMyfooddiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        if (intent.hasExtra("fooditem_edit")) {
            edit = true
            foodItem = intent.extras?.getParcelable("fooditem_edit")!!
            binding.foodTitle.setText(foodItem.title)
            binding.description.setText(foodItem.description)
            binding.btnAdd.setText(R.string.save_fooditem)
            Picasso.get()
                .load(foodItem.image)
                .into(binding.foodImage)
        }

        binding.btnAdd.setOnClickListener() {
            foodItem.title = binding.foodTitle.text.toString()
            foodItem.description = binding.description.text.toString()
            if (foodItem.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_fooditem_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.foodItems.update(foodItem.copy())
                } else {
                    app.foodItems.create(foodItem.copy())
                }
            }
            i("add Button Pressed: $foodItem")
            setResult(RESULT_OK)
            finish()
        }
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            foodItem.image = result.data!!.data!!
                            Picasso.get()
                                .load(foodItem.image)
                                .into(binding.foodImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}



