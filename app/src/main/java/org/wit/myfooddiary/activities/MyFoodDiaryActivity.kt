package org.wit.myfooddiary.activities

import android.content.Intent

import android.net.Uri
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
import org.wit.myfooddiary.models.Location
import org.wit.myfooddiary.models.UserModel
import timber.log.Timber.i
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyFoodDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyfooddiaryBinding
    var foodItem = FoodModel()
    var user = UserModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityMyfooddiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.amountOfCals.minValue = 1
        binding.amountOfCals.maxValue = 1000

        binding.amountOfCals.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
           // donateLayout.paymentAmount.setText("$newVal")
            foodItem.amountOfCals = newVal
        }
        if (intent.hasExtra("fooditem_edit")) {
            edit = true
            foodItem = intent.extras?.getParcelable("fooditem_edit")!!
            binding.foodTitle.setText(foodItem.title)
            binding.description.setText(foodItem.description)
            binding.btnAdd.setText(R.string.save_fooditem)
            Picasso.get()
                .load(foodItem.image)
                .into(binding.foodImage)
            if (foodItem.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_food_image)
            }
        }

        if (intent.hasExtra("foodItem_create")) {
            user = intent.extras?.getParcelable("foodItem_create")!!
        }

        binding.btnAdd.setOnClickListener() {
            foodItem.fUid = user.Uid
            foodItem.title = binding.foodTitle.text.toString()
            foodItem.description = binding.description.text.toString()
            foodItem.timeForFood = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))
            foodItem.amountOfCals = binding.amountOfCals.value
            if (foodItem.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_fooditem_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.foodItems.update(foodItem.copy())
                } else {
                    app.foodItems.create(foodItem.copy(), user)
                }
            }
            i("add Button Pressed: $foodItem")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.foodItemLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (foodItem.zoom != 0f) {
                location.lat =  foodItem.lat
                location.lng = foodItem.lng
                location.zoom = foodItem.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        registerMapCallback()

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
        when (item.itemId){
            R.id.item_updateuser -> {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        when (item.itemId){
            R.id.item_logout -> {
                val intent = Intent(this, LoginsActivity::class.java)
                startActivity(intent)
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
                            binding.chooseImage.setText(R.string.change_food_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            foodItem.lat = location.lat
                            foodItem.lng = location.lng
                            foodItem.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}



