//package org.wit.myfooddiary.activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Patterns
//import android.view.Menu
//import android.view.MenuItem
//import com.google.android.material.snackbar.Snackbar
//import org.wit.myfooddiary.R
//import org.wit.myfooddiary.databinding.ActivitySignupBinding
//import org.wit.myfooddiary.main.MainApp
//import org.wit.myfooddiary.models.UserModel
//import timber.log.Timber
//
//class SignupActivity : AppCompatActivity() {
//    private lateinit var binding: ActivitySignupBinding
//    var user = UserModel()
//    private lateinit var app: MainApp
//    var edit = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivitySignupBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.toolbarAdd.title = title
//        setSupportActionBar(binding.toolbarAdd)
//        app = application as MainApp
//
//        if (intent.hasExtra("user_edit")) {
//            edit = true
//            user = intent.extras?.getParcelable("user_edit")!!
//            binding.firstName.setText(user.firstName)
//            binding.lastName.setText(user.lastName)
//            binding.email.setText(user.email)
//            binding.password.setText(user.password)
//        }
//
//        binding.btnConfirm.setOnClickListener() {
//            user.firstName = binding.firstName.text.toString()
//            user.lastName = binding.lastName.text.toString()
//            user.password = binding.password.text.toString()
//            user.email = binding.email.text.toString()
//            if (user.firstName.isEmpty()) {
//                Snackbar.make(it, R.string.enter_user_name, Snackbar.LENGTH_LONG)
//                    .show()
//            }
//            if (user.lastName.isEmpty()) {
//                Snackbar.make(it, R.string.enter_last_name, Snackbar.LENGTH_LONG)
//                    .show()
//            }
//            if (user.email.isEmpty() || user.email.isEmailValid()) {
//                Snackbar.make(it, R.string.enter_email, Snackbar.LENGTH_LONG)
//                    .show()
//            }
//            if (user.password.isEmpty()) {
//                Snackbar.make(it, R.string.enter_password, Snackbar.LENGTH_LONG)
//                    .show()
//            }
//            if (edit == true) {
//                app.users.updateUser(user.copy())
//                Snackbar.make(it, R.string.user_updated, Snackbar.LENGTH_LONG)
//                    .show()
//                val intent = Intent(this, MyFoodDiaryActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                val iEditUser = app.users.createUser(user.copy())
//                if (iEditUser == null) {
//                    Snackbar.make(it, R.string.email_exists, Snackbar.LENGTH_LONG)
//                        .show()
//                } else {
//                    val intent = Intent(this, FoodListActivity::class.java).apply {
//                        putExtra("user_signup", iEditUser)
//                    }
//                    startActivity(intent)
//                    finish()
//                }
//            }
//            Timber.i("add User Button Pressed: $user")
//            setResult(RESULT_OK)
//        }
//    }
//
//    fun String.isEmailValid(): Boolean {
//        return !Patterns.EMAIL_ADDRESS.matcher(this).matches()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_signup, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (edit ==true) {
//            when (item.itemId) {
//                R.id.item_cancel -> {
//                    finish()
//                }
//            }
//        }else{
//            when (item.itemId) {
//                R.id.item_cancel -> {
//                    val intent = Intent(this, LoginsActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//}
//
