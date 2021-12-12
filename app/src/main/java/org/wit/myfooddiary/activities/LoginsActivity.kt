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
//import org.wit.myfooddiary.databinding.ActivityLoginsBinding
//import org.wit.myfooddiary.main.MainApp
//import org.wit.myfooddiary.models.UserModel
//import timber.log.Timber
//
//class LoginsActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityLoginsBinding
//    var user = UserModel()
//    lateinit var app: MainApp
//    val IMAGE_REQUEST = 1
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        var edit = false
//        binding = ActivityLoginsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.toolbarAdd.title = title
//        setSupportActionBar(binding.toolbarAdd)
//
//        app = application as MainApp
//        if (intent.hasExtra("user_edit")) {
//            edit = true
//            user = intent.extras?.getParcelable("user_edit")!!
//            binding.password.setText(user.password)
//            binding.email.setText(user.email)
//            binding.btnLogin.setText(R.string.login_user)
//
//        }
//
//        binding.btnLogin.setOnClickListener() {
//            user.password = binding.password.text.toString()
//            user.email = binding.email.text.toString()
//
//            if (user.email.isEmailValid() || user.email.isEmpty()) {
//                Snackbar.make(it, R.string.enter_email, Snackbar.LENGTH_LONG)
//                    .show()
//
//            } else if(user.password.isEmpty()){
//                Snackbar.make(it, R.string.enter_password, Snackbar.LENGTH_LONG)
//                    .show()
//            } else {
//                if (edit) {
//                    app.users.updateUser(user.copy())
//                } else {
//                    val ans = app.users.checkCredientials(user.copy())
//                    if (ans == null) {
//                        Snackbar.make(it, R.string.wrong_password, Snackbar.LENGTH_LONG)
//                            .show()
//                    } else {
//                        val intent = Intent(this, FoodListActivity::class.java).apply {
//                            putExtra("user_signup", ans)
//                        }
//                        startActivity(intent)
//                        finish()
//                    }
//                }
//            }
//            Timber.i("add Button Pressed: $user")
//            setResult(RESULT_OK)
//
//        }
//
//    }
//    fun String.isEmailValid(): Boolean {
//        return !Patterns.EMAIL_ADDRESS.matcher(this).matches()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_login, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.item_signup -> {
//                startActivity(Intent(this, SignupActivity::class.java))
//                finish()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//}