package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.myfooddiary.R
import org.wit.myfooddiary.adapters.FoodItemListener
import org.wit.myfooddiary.adapters.MyFoodDiaryAdapter
import org.wit.myfooddiary.databinding.ActivityFoodListBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.UserModel

class FoodListActivity : AppCompatActivity(), FoodItemListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFoodListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var signedUp = false
    var foodItem = FoodModel()
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        if (intent.hasExtra("user_signup")) {
            signedUp = true

//            // Get the Intent that started this activity and extract the string
//            val message = intent.extras?.getParcelable("user_signup")!!
//
//            // Capture the layout's TextView and set the string as its text
//            val textView = findViewById<TextView>(R.id.textView).apply {
//                text = message
//            }
            user = intent.extras?.getParcelable("user_signup")!!
           // binding.welcomeMessage.setText("Welcome " + foodItem.firstName)
        }



        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadFoodItems(user.Uid)
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, MyFoodDiaryActivity::class.java).apply {
                    putExtra("foodItem_create", user)
                }
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFoodItemClick(foodItem: FoodModel) {
            val launcherIntent = Intent(this, MyFoodDiaryActivity::class.java)
            launcherIntent.putExtra("fooditem_edit", foodItem)
            refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onFoodItemDelete(foodItem: FoodModel) {
        app.foodItems.deleteItem(foodItem)
        registerRefreshCallback()
    }


    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadFoodItems(user.Uid) }
    }

    private fun loadFoodItems(id: Long) {
        var ans = app.foodItems.findAllById(id)
        showFoodItems(ans)
    }

    fun showFoodItems (foodItems: List<FoodModel>) {
        binding.recyclerView.adapter = MyFoodDiaryAdapter(foodItems, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}
