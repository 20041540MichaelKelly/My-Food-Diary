package org.wit.myfooddiary.ui.fooddiary

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.activities.MapActivity
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.helpers.checkLocationPermissions
import org.wit.myfooddiary.helpers.showImagePicker
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.Location
import org.wit.myfooddiary.models.UserModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import timber.log.Timber
import timber.log.Timber.i
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MyFoodDiaryFragmentPresenter (private val view: MyFoodDiaryFragmentView) {
    private var _fragBinding: FragmentMyFoodDiaryBinding = FragmentMyFoodDiaryBinding.inflate(view.layoutInflater)
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    var user = UserModel()
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var edit = false
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    var lastId = 0L
    private var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.requireActivity())
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    var buttonPressed = false

    internal fun getId(): Long {
    return lastId++
}

    init {
       // doPermissionLauncher()
        registerImagePickerCallback()
        registerMapCallback()



    }

    fun doAddOrSave(title: String, description: String) {
        foodItem.title = title
        foodItem.description = description

    }

    fun doCancel() {
     //   view.finish()

    }

    fun doDelete() {
//        app.placemarks.delete(placemark)
    //    view.finish()

    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }


    fun cacheFooodLocation (title: String, description: String) {
        foodItem.title = title;
        foodItem.description = description
    }

     fun setButtonListener(
        layout: FragmentMyFoodDiaryBinding,
        loggedInViewModel: LoggedInViewModel
    ) {
        layout.btnAdd.setOnClickListener() {
            foodItem.title = layout.foodTitle.text.toString()
            foodItem.description = layout.description.text.toString()
            foodItem.timeForFood =LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))
            foodItem.amountOfCals = layout.amountOfCals.value
            if (foodItem.description.isEmpty()) {
                Snackbar.make(it, R.string.enter_fooditem_title, Snackbar.LENGTH_LONG)
                    .show()
            }

            view.myFoodDiaryViewModel.addFoodItem(
                loggedInViewModel.liveFirebaseUser,
                FoodModel(
                    title = layout.foodTitle.text.toString(),
                    description = layout.description.text.toString(),
                    amountOfCals = layout.amountOfCals.value,
                    dateLogged = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("M/d/y H:m:ss")),
                    timeForFood = System.currentTimeMillis().toString(),
                    image = foodItem.image,
                    email = loggedInViewModel.liveFirebaseUser.value?.email!!,
                    lat = foodItem.lat,
                    lng = foodItem.lng,
                    zoom = 15f
                )
            )

        }

        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        layout.captureImage.setOnClickListener {
            dispatchTakePictureIntent()
        }

        layout.foodItemLocation.setOnClickListener {
            if (checkLocationPermissions(view.requireActivity())) {

                doSetCurrentLocation()

            }

        }
        registerImagePickerCallback()
        registerMapCallback()

    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            foodItem.image = result.data!!.data!!.toString()
                            Picasso.get()
                                .load(foodItem.image)
                                .into(view.fragBinding.foodImage)

                            fragBinding.chooseImage.setText(R.string.change_food_image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val foods = result.data!!.extras?.getParcelable<FoodModel>("location")!!
                            i("Location == $foods")
                            foodItem.lat = foods.lat
                            foodItem.lng = foods.lng
                            foodItem.zoom = foods.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = view.activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent

            takePictureIntent.resolveActivity(view.activity!!.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Timber.i("Take Picture Error : $ex.message")
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        view.requireActivity(),
                        "org.wit.myfooddiary.fileprovider",
                        it
                    )
                    foodItem.image = photoURI.toString()
                    Picasso.get()
                        .load(foodItem.image)
                        .into(fragBinding.foodImage)

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    view.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun doPermissionLauncher() {
        i("permission check called")
        requestPermissionLauncher =
            view.registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    doSetCurrentLocation()
                } else {
                    locationUpdate(52.4484, 6.8642)
                }
            }
    }

    fun locationUpdate(lat: Double, lng: Double) {
        val foodLoc = FoodModel(lat = lat,lng =  lng, zoom = 15f)



        val launcherIntent = Intent(view.getActivity(), MapActivity::class.java)
            .putExtra("location", foodLoc)

        mapIntentLauncher.launch(launcherIntent)

    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        i("setting location from doSetLocation")
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }


}