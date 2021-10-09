package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityLoginsBinding
import org.wit.myfooddiary.databinding.ActivityMyfooddiaryBinding
import org.wit.myfooddiary.helpers.showImagePicker
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.UserModel
import timber.log.Timber

class LoginsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginsBinding
    var user = UserModel()
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
        if (intent.hasExtra("user_edit")) {
            edit = true
            user = intent.extras?.getParcelable("user_edit")!!
            binding.username.setText(user.name)
            binding.email.setText(user.email)
            binding.btnAdd.setText(R.string.save_user)

        }

        binding.btnAdd.setOnClickListener() {
            user.name = binding.username.text.toString()
            user.email = binding.email.text.toString()
            if (user.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_user_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.users.update(user.copy())
                } else {
                    app.users.create(user.copy())
                }
            }
            Timber.i("add Button Pressed: $user")
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