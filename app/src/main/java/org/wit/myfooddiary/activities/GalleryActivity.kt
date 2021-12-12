//package org.wit.myfooddiary.activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.squareup.picasso.Picasso
//import org.wit.myfooddiary.R
//import org.wit.myfooddiary.adapters.FoodItemListener
//import org.wit.myfooddiary.adapters.GalleryAdapter
//import org.wit.myfooddiary.databinding.ActivityGalleryBinding
//import org.wit.myfooddiary.main.MainApp
//import org.wit.myfooddiary.models.FoodModel
//
//class GalleryActivity : AppCompatActivity(), FoodItemListener {
//    lateinit var app: MainApp
//    private lateinit var binding: ActivityGalleryBinding
//    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityGalleryBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.toolbar.title = title
//        setSupportActionBar(binding.toolbar)
//
//        app = application as MainApp
//
//        val layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.layoutManager = layoutManager
////        loadPictures()
////        registerRefreshCallback()
//    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_fooditem, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.item_cancel -> {
//                finish()
//            }
//        }
//        when (item.itemId){
//            R.id.item_updateuser -> {
//                val intent = Intent(this, SignupActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//        when (item.itemId){
//            R.id.item_logout -> {
//                val intent = Intent(this, LoginsActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
////    private fun loadPictures() {
////        val receivePics = app.foodItems.findAll()
////        showPictures(receivePics)
////    }
//
////    private fun registerRefreshCallback() {
////        refreshIntentLauncher =
////            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
////            { loadPictures() }
////    }
//
//    private fun showPictures(foodItems: List<FoodModel>) {
//        binding.recyclerView.adapter = GalleryAdapter(foodItems, this)
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//    }
//
//    override fun onFoodItemClick(foodItem: FoodModel) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onFoodItemDelete(foodItem: FoodModel) {
//        TODO("Not yet implemented")
//    }
//}