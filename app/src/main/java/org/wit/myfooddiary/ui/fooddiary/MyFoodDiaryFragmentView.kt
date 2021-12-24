package org.wit.myfooddiary.ui.fooddiary

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.activities.MapActivity
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.helpers.showImagePicker
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.Location
import org.wit.myfooddiary.models.UserModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.camera.Camera
import org.wit.myfooddiary.ui.foodlist.MyFoodListFragmentDirections
import timber.log.Timber
import timber.log.Timber.i
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MyFoodDiaryFragmentView : Fragment() {
    private var _fragBinding: FragmentMyFoodDiaryBinding? = null
    val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    var user = UserModel()
    lateinit var myFoodDiaryViewModel: MyFoodDiaryViewModel
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    val camera = Camera()
    val IMAGE_REQUEST = 1
    var edit = false
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    val REQUEST_CODE = 200
    private lateinit var presenter: MyFoodDiaryFragmentPresenter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMyFoodDiaryBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        // activity?.title = getString(R.string.action_myfooddiary)
        presenter = MyFoodDiaryFragmentPresenter(this)

        fragBinding.chooseImage.setOnClickListener {
            presenter.cacheFooodLocation(fragBinding.foodTitle.text.toString(), fragBinding.description.text.toString())
            presenter.doSelectImage()
        }

        myFoodDiaryViewModel = ViewModelProvider(this).get(MyFoodDiaryViewModel::class.java)
        myFoodDiaryViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { render(status) }
        })
        fragBinding.amountOfCals.minValue = 1
        fragBinding.amountOfCals.maxValue = 1000

        fragBinding.amountOfCals.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            // donateLayout.paymentAmount.setText("$newVal")
            foodItem.amountOfCals = newVal
        }

        presenter.setButtonListener(fragBinding, loggedInViewModel)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfooddiary, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(fragBinding.foodImage)
        fragBinding.chooseImage.setText(R.string.change_food_image)
    }

     fun foodItemLoc(foodItem : FoodModel) {
         //if(foodItem.fid != ""){
        val action = MyFoodDiaryFragmentViewDirections.actionMyFoodDiaryFragmentToFoodLocation()
           //foodItem.fid!!.toLong())
        findNavController().navigate(action)
    }


}