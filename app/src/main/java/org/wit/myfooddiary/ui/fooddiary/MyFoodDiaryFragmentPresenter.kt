package org.wit.myfooddiary.ui.fooddiary

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.camera.Camera
import timber.log.Timber
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

    // lateinit var app: MainApp
    private lateinit var myFoodDiaryViewModel: MyFoodDiaryViewModel
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    val camera = Camera()
    val IMAGE_REQUEST = 1
    var edit = false
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    val REQUEST_CODE = 200

    init {
//        if (view.getActivity().intent.hasExtra("placemark_edit")) {
//            edit = true
//            placemark = view.intent.extras?.getParcelable("placemark_edit")!!
//            view.showPlacemark(placemark)
//        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String) {
        foodItem.title = title
        foodItem.description = description
//        if (edit) {
//            //app.placemarks.update(foodItem)
//        } else {
//           // app.placemarks.create(foodItem)
//        }
//
 //       view.finish()

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

    fun doSetLocation() {
//        val location = Location(52.245696, -7.139102, 15f)
//        if (placemark.zoom != 0f) {
//            location.lat =  placemark.lat
//            location.lng = placemark.lng
//            location.zoom = placemark.zoom
//        }
//        val launcherIntent = Intent(view, EditLocationView::class.java)
//            .putExtra("location", location)
//        mapIntentLauncher.launch(launcherIntent)
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
            foodItem.timeForFood =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))
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
                    timeForFood = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("M/d/y H:m:ss")),
                    image = foodItem.image,
                    email = loggedInViewModel.liveFirebaseUser.value?.email!!,
                    lat = 52.245696,
                    lng = -7.139102,
                    zoom = 15f
                )
            )

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

        layout.captureImage.setOnClickListener {
            dispatchTakePictureIntent()
        }

        layout.foodItemLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (foodItem.zoom != 0f) {
                location.lat = foodItem.lat
                location.lng = foodItem.lng
                location.zoom = foodItem.zoom
            }
            val launcherIntent = Intent(view.getActivity(), MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
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
                                .rotate(90F)
                                .into(fragBinding.foodImage)

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
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            foodItem.lat = location.lat
                            foodItem.lng = location.lng
                            foodItem.zoom = location.zoom
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


}