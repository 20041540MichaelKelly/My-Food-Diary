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
import org.wit.myfooddiary.models.UserModel
import timber.log.Timber

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var user = UserModel()
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
            user.firstName = binding.firstName.text.toString()
            user.lastName = binding.lastName.text.toString()
            user.password = binding.password.text.toString()
            user.email = binding.email.text.toString()
            if (user.firstName.isEmpty()) {
                Snackbar.make(it, R.string.enter_user_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            if(user.lastName.isEmpty()) {
                Snackbar.make(it, R.string.enter_last_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            if(user.email.isEmpty()) {
                Snackbar.make(it, R.string.enter_email, Snackbar.LENGTH_LONG)
                    .show()
            }
            if(user.password.isEmpty()) {
                Snackbar.make(it, R.string.enter_password, Snackbar.LENGTH_LONG)
                    .show()
            }
                var ans = app.users.createUser(user.copy())
                val intent = Intent(this, FoodListActivity::class.java).apply {
                    putExtra("user_signup", ans)
                }
                startActivity(intent)


            Timber.i("add User Button Pressed: $user")
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

