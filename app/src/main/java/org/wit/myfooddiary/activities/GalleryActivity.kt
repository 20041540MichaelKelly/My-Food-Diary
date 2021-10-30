package org.wit.myfooddiary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityGalleryBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel

class GalleryActivity : AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: ActivityGalleryBinding
    var foodItem = FoodModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp
        loadPictures()
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
        when (item.itemId){
            R.id.item_updateuser -> {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        when (item.itemId){
            R.id.item_logout -> {
                val intent = Intent(this, LoginsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadPictures() {
        val receivePics =  app.foodItems.findAll()
        showPictures(receivePics)
    }

    private fun showPictures(foodItems: List<FoodModel>) {
        for (f in foodItems) {
            Picasso.get().load(f.image).resize(200, 200).into(binding.imageIcon)
        }

    }
}