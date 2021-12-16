package org.wit.myfooddiary.ui.fooddiary

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.activities.MapActivity
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
   // lateinit var app: MainApp
   private lateinit var myFoodDiaryViewModel: MyFoodDiaryViewModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMyFoodDiaryBinding.inflate(inflater, container, false)
        val root = fragBinding.root
       // activity?.title = getString(R.string.action_myfooddiary)

        myFoodDiaryViewModel = ViewModelProvider(this).get(MyFoodDiaryViewModel::class.java)
        myFoodDiaryViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        fragBinding.amountOfCals.minValue = 1
        fragBinding.amountOfCals.maxValue = 1000

        fragBinding.amountOfCals.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            // donateLayout.paymentAmount.setText("$newVal")
            foodItem.amountOfCals = newVal
        }
//        if (getActivity()?.getIntent()?.getExtras()?.getString("fooditem_edit") != null) {
//            edit = true
//            foodItem = activity?.intent?.extras?.getParcelable("fooditem_edit")!!
//            fragBinding.foodTitle.setText(foodItem.title)
//            fragBinding.description.setText(foodItem.description)
//            fragBinding.btnAdd.setText(R.string.save_fooditem)
//            Picasso.get()
//                .load(foodItem.image)
//                .into(fragBinding.foodImage)
//            if (foodItem.image != Uri.EMPTY) {
//                fragBinding.chooseImage.setText(R.string.change_food_image)
//            }
//        }

        setButtonListener(fragBinding)

        return root;
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context, getString(R.string.foodItemError), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun setButtonListener(layout: FragmentMyFoodDiaryBinding) {
        layout.btnAdd.setOnClickListener() {
            foodItem.title = layout.foodTitle.text.toString()
            foodItem.description = layout.description.text.toString()
            foodItem.timeForFood =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))
            foodItem.amountOfCals = layout.amountOfCals.value
            if (foodItem.description.isEmpty()) {
                Snackbar.make(it, R.string.enter_fooditem_title, Snackbar.LENGTH_LONG)
                    .show()
            }
            myFoodDiaryViewModel.addFoodItem(foodItem.copy())
            myFoodDiaryViewModel.addFoodItem(foodItem.copy())

//            } else {
//                if (edit) {
//                    layout.foodItems.update(foodItem.copy())
//                } else {
//                    foodItem.fUid = user.Uid
//                 //   app.foodItems.create(foodItem.copy(), user)
//                    app.foodItems.create(foodItem.copy())
//                }
//            }
//            Timber.i("add Button Pressed: $foodItem")
//            getActivity()?.setResult(AppCompatActivity.RESULT_OK);
//            getActivity()?.finish();
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
            val launcherIntent = Intent(getActivity(), MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)

        }
        registerImagePickerCallback()
       // registerMapCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfooddiary, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            foodItem.image = result.data!!.data!!.toString()
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
//
//    private fun registerMapCallback() {
//        mapIntentLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//            { result ->
//                when (result.resultCode) {
//                    AppCompatActivity.RESULT_OK -> {
//                        if (result.data != null) {
//                            Timber.i("Got Location ${result.data.toString()}")
//                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
//                            Timber.i("Location == $location")
//                            foodItem.lat = location.lat
//                            foodItem.lng = location.lng
//                            foodItem.zoom = location.zoom
//                        } // end of if
//                    }
//                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
//                }
//            }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}