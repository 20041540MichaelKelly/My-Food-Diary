package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.myfooddiary.R
import org.wit.myfooddiary.adapters.FoodItemListener
import org.wit.myfooddiary.adapters.MyFoodDiaryAdapter
import org.wit.myfooddiary.databinding.ActivityFoodListBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel

class FoodListActivity : AppCompatActivity(), FoodItemListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFoodListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = MyFoodDiaryAdapter(app.foodItems.findAll(),this)

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, MyFoodDiaryActivity::class.java)
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

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}
