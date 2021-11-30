package org.wit.myfooddiary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.helpers.showImagePicker
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.Location
import org.wit.myfooddiary.models.UserModel
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyFoodDiaryFragment : Fragment() {
    private var _fragBinding: FragmentMyFoodDiaryBinding? = null
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    var user = UserModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMyFoodDiaryBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_myfooddiary)

        fragBinding.amountOfCals.minValue = 1
        fragBinding.amountOfCals.maxValue = 1000

        fragBinding.amountOfCals.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            // donateLayout.paymentAmount.setText("$newVal")
            foodItem.amountOfCals = newVal
        }
        if (getActivity()?.getIntent()?.getExtras()?.getString("fooditem_edit") != null) {
            edit = true
            foodItem = activity?.intent?.extras?.getParcelable("fooditem_edit")!!
            fragBinding.foodTitle.setText(foodItem.title)
            fragBinding.description.setText(foodItem.description)
            fragBinding.btnAdd.setText(R.string.save_fooditem)
            Picasso.get()
                .load(foodItem.image)
                .into(fragBinding.foodImage)
            if (foodItem.image != Uri.EMPTY) {
                fragBinding.chooseImage.setText(R.string.change_food_image)
            }
        }

//        if (activity?.intent?.hasExtra("foodItem_create") != null) {
//            user = activity?.intent?.extras?.getParcelable("foodItem_create")!!
//        }

        setButtonListener(fragBinding)

        return root;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()

    }

    fun setButtonListener(layout: FragmentMyFoodDiaryBinding) {
        layout.btnAdd.setOnClickListener() {
            foodItem.title = fragBinding.foodTitle.text.toString()
            foodItem.description = fragBinding.description.text.toString()
            foodItem.timeForFood = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))
            foodItem.amountOfCals = fragBinding.amountOfCals.value
            if (foodItem.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_fooditem_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.foodItems.update(foodItem.copy())
                } else {
                    foodItem.fUid = user.Uid
                    app.foodItems.create(foodItem.copy(), user)
                }
            }
            Timber.i("add Button Pressed: $foodItem")
            //setResult(AppCompatActivity.RESULT_OK)
            //finish()
        }

        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        layout.foodItemLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (foodItem.zoom != 0f) {
                location.lat =  foodItem.lat
                location.lng = foodItem.lng
                location.zoom = foodItem.zoom
            }

        }
        registerImagePickerCallback()
        registerMapCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfooddiary, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            foodItem.image = result.data!!.data!!
                            Picasso.get()
                                .load(foodItem.image)
                                .into(fragBinding.foodImage)
                            fragBinding.chooseImage.setText(R.string.change_food_image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            foodItem.lat = location.lat
                            foodItem.lng = location.lng
                            foodItem.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}