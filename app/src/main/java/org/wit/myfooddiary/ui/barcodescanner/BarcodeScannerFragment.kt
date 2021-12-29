package org.wit.myfooddiary.ui.barcodescanner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FragmentBarcodeScannerBinding
import org.wit.myfooddiary.helpers.HelperActivity

class BarcodeScannerFragment : Fragment() {

    private lateinit var fragBinding: FragmentBarcodeScannerBinding
    private lateinit var qrScanIntegrator: IntentIntegrator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragBinding = FragmentBarcodeScannerBinding.inflate(layoutInflater)
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
//                    val imageString = obj.getString("image")
//                    Picasso.get()
//                        .load(imageString)
//                        .into(fragBinding.image)

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
}
