package org.wit.myfooddiary.ui.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FoodMapBinding
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.models.FoodModel

class FoodLocationFragmentPresenter(val view: FoodLocationFragmentView) {

    private var _fragBinding: FoodMapBinding = FoodMapBinding.inflate(view.layoutInflater)
    private val fragBinding get() = _fragBinding
    private var map: GoogleMap? = null
    var foodItem = FoodModel()
    private lateinit var foodLocationViewModel: FoodLocationViewModel
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

init {
    registerMapCallback()
    registerRefreshCallback()
}
    fun doConfigureMap(m: GoogleMap) {
        map = m

        //view.configureMap()
    }

    fun locationUpdate(foodItems:  List<FoodModel>) {
        foodItems.forEach { foodItem ->
            foodItem.zoom = 15f
            map?.clear()
            map?.uiSettings?.setZoomControlsEnabled(true)
            val options =
                MarkerOptions().title(foodItem.title).position(LatLng(foodItem.lat, foodItem.lng))
            map?.addMarker(options)
            map?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(foodItem.lat, foodItem.lng),
                    foodItem.zoom
                )
            )
            view.showLocations(foodItem)
        }
    }



    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {

            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }



//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//        val loc = LatLng(location.lat, location.lng)
//        val options = MarkerOptions()
//            .title("Food")
//            .snippet("GPS : $loc")
//            .draggable(true)
//            .position(loc)
//        map.setOnMarkerClickListener(this)
//        map.addMarker(options)
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
//        map.setOnMarkerDragListener(this)
//    }
//


}
