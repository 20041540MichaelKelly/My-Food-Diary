package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityLoginsBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import timber.log.Timber

class LoginsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginsBinding
    var foodItem = FoodModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityLoginsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        if (intent.hasExtra("foodItem_edit")) {
            edit = true
            foodItem = intent.extras?.getParcelable("foodItem_edit")!!
            binding.password.setText(foodItem.password)
            binding.email.setText(foodItem.email)
            binding.btnLogin.setText(R.string.login_user)

        }

        binding.btnLogin.setOnClickListener() {
            foodItem.password = binding.password.text.toString()
            foodItem.email = binding.email.text.toString()

            if (foodItem.password.isEmpty()) {
                Snackbar.make(it, R.string.enter_password, Snackbar.LENGTH_LONG)
                    .show()

            } else {
                if (edit) {
                    app.foodItems.updateUser(foodItem.copy())
                } else {
                    var ans = app.foodItems.checkCredientials(foodItem.copy())
                    if (ans == null) {
                        Snackbar.make(it, R.string.wrong_password, Snackbar.LENGTH_LONG)
                            .show()
                    } else {
                        // app.users.createUser(user.copy())
//                        var result = app.foodItems.findOneUser(ans.Uid)
                        val intent = Intent(this, FoodListActivity::class.java).apply {
                            putExtra("foodItem_signup", ans)
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
            Timber.i("add Button Pressed: $foodItem")
            setResult(RESULT_OK)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_login, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_signup -> {
                startActivity(Intent(this, SignupActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}