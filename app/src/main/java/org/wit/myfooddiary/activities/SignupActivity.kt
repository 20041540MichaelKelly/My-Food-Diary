package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivitySignupBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var foodItem = FoodModel()
    private lateinit var app: MainApp
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.btnConfirm.setOnClickListener() {
            foodItem.firstName = binding.firstName.text.toString()
            foodItem.lastName = binding.lastName.text.toString()
            foodItem.password = binding.password.text.toString()
            foodItem.email = binding.email.text.toString()
            if (foodItem.firstName.isEmpty()) {
                Snackbar.make(it, R.string.enter_user_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            if(foodItem.lastName.isEmpty()) {
                Snackbar.make(it, R.string.enter_last_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            if(foodItem.email.isEmpty()) {
                Snackbar.make(it, R.string.enter_email, Snackbar.LENGTH_LONG)
                    .show()
            }
            if(foodItem.password.isEmpty()) {
                Snackbar.make(it, R.string.enter_password, Snackbar.LENGTH_LONG)
                    .show()
            }
                app.foodItems.createUser(foodItem.copy())
                val intent = Intent(this, FoodListActivity::class.java).apply {
                    putExtra("foodItem_signup", foodItem)
                }
                startActivity(intent)


            Timber.i("add User Button Pressed: $foodItem")
            setResult(RESULT_OK)
            finish()
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

