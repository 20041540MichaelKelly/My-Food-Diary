package org.wit.myfooddiary.ui.barcodescanner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FragmentBarcodeScannerBinding
import org.wit.myfooddiary.helpers.HelperActivity
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.apifoodlist.ApiFoodListViewModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.fooddiary.MyFoodDiaryViewModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BarcodeScannerFragment : Fragment() {

    private lateinit var fragBinding: FragmentBarcodeScannerBinding
    private lateinit var qrScanIntegrator: IntentIntegrator
     var foodItem = FoodModel()

    lateinit var myFoodDiaryViewModel: MyFoodDiaryViewModel

    private val loggedInViewModel: LoggedInViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentBarcodeScannerBinding.inflate(layoutInflater)
        myFoodDiaryViewModel = ViewModelProvider(this).get(MyFoodDiaryViewModel::class.java)
        fragBinding.addBtn.setVisibility(View.GONE)

        return fragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScanner()
        setOnClickListener()
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator.setOrientationLocked(false)
    }

    private fun setOnClickListener() {
        fragBinding.btnScan.setOnClickListener {
            performAction()
        }

        fragBinding.showQRScanner.setOnClickListener {
            // Add code to show QR Scanner Code in Fragment.
            startActivity(Intent(context, HelperActivity::class.java))
        }

        fragBinding.addBtn.setOnClickListener{
            addToFirebaseDB(foodItem)

        }
    }

    private fun performAction() {
        // Code to perform action when button is clicked.
        qrScanIntegrator.initiateScan()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(activity, R.string.result_not_found, Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)

                    // Show values in UI.
                    fragBinding.title.text = obj.getString("title")
                    fragBinding.description.text = obj.getString("description")
                    fragBinding.amountOfCals.text = obj.getString("amountOfCals")
                    fragBinding.lat.text = obj.getString("lat")
                    fragBinding.lng.text = obj.getString("lng")
                    val imageString = obj.getString("image")
                    Picasso.get()
                        .load(imageString)
                        .into(fragBinding.image)

                    foodItem.title = obj.getString("title")
                    foodItem.description= obj.getString("description")
                    val calIntVal = obj.getString("amountOfCals")      //Gathering the data to be added to my list
                    foodItem.amountOfCals = calIntVal.toInt()
                    val latInString = obj.getString("lat")
                    foodItem.lat = latInString.toDouble()
                    foodItem.lng = obj.getString("lng").toDouble()
                    foodItem.image = obj.getString("image")

                    fragBinding.addBtn.setVisibility(View.VISIBLE);
                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun addToFirebaseDB(foodItem: FoodModel) {
        myFoodDiaryViewModel.addFoodItem(
            loggedInViewModel.liveFirebaseUser,
            FoodModel(
                title = foodItem.title,
                description = foodItem.description,
                amountOfCals = foodItem.amountOfCals,
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
        Toast.makeText(context,"Food Item added",Toast.LENGTH_LONG).show()

    }
}
