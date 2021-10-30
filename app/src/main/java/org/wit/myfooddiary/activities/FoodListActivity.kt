package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
    var user = UserModel()
    var searchReq = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        if (intent.hasExtra("user_signup")) {
            signedUp = true

            user = intent.extras?.getParcelable("user_signup")!!
        }

        binding.searchButton.setOnClickListener(){
            searchReq = binding.searchValue.text.toString()
            onSearchItem(searchReq)
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
        foodItem.fUid = user.Uid
        val launcherIntent = Intent(this, MyFoodDiaryActivity::class.java)
        launcherIntent.putExtra("fooditem_edit", foodItem)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onFoodItemDelete(foodItem: FoodModel) {
        app.foodItems.deleteItem(foodItem)
        loadFoodItems(user.Uid)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadFoodItems(user.Uid) }
    }

    private fun loadFoodItems(id: Long) {
        val ans = app.foodItems.findAllById(id)
        showFoodItems(ans)
    }

    private fun seacrhItems(searchValue: String){
//        var ans = app.foodItems.filter(searchValue)

    }

    fun showFoodItems (foodItems: List<FoodModel>) {
        binding.recyclerView.adapter = MyFoodDiaryAdapter(foodItems, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    fun onSearchItem(searchValue: String){
        val iSearchVal = app.foodItems.findAllBySearchValue(searchValue)
        if (iSearchVal == null){
            Toast.makeText(this, "No result for search value", Toast.LENGTH_LONG).show()
        }else{
            showFoodItems(iSearchVal)
        }
    }
}
