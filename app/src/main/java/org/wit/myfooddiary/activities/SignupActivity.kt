package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivitySignupBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.UserModel
import timber.log.Timber

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
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
        /*if (intent.hasExtra("user_edit")) {
            edit = true
            user = intent.extras?.getParcelable("user_edit")!!

            binding.lastName.setText(user.lastName)
            binding.firstName.setText(user.firstName)
            binding.password.setText(user.password)
            binding.email.setText(user.email)
            binding.btnConfirm.setText(R.string.save_user)

        }*/

        binding.btnConfirm.setOnClickListener() {
            user.firstName = binding.firstName.text.toString()
            user.lastName = binding.lastName.text.toString()
            user.password = binding.password.text.toString()
            user.email = binding.email.text.toString()
            if (user.password.isEmpty()) {
                Snackbar.make(it, R.string.enter_user_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                app.users.create(user.copy())
                val intent = Intent(this, MyFoodDiaryActivity::class.java)
                startActivity(intent)
                finish()

            }

            Timber.i("add User Button Pressed: $user")
            setResult(RESULT_OK)

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