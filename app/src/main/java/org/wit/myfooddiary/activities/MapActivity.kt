package org.wit.myfooddiary.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.myfooddiary.R
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.Location

//import org.wit.myfooddiary.models.Location

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    var location = Location()
    var foodItem = FoodModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        if (intent.hasExtra("location")) {
            location = intent.extras?.getParcelable<Location>("location")!!
        }else{
            location = Location(52.245696, -7.139102, 15f)
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Food")
            .snippet("GPS : $loc")
            .draggable(true)
            .position(loc)
        map.setOnMarkerClickListener(this)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        map.setOnMarkerDragListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val loc = LatLng(location.lat, location.lng)
        marker.snippet = "GPS : $loc"
        return false
    }

    override fun onMarkerDrag(marker: Marker) {
        foodItem.lat += marker.position.latitude
        foodItem.lng += marker.position.longitude
        foodItem.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerDragEnd(marker: Marker) {
        foodItem.lat = marker.position.latitude
        foodItem.lng = marker.position.longitude
        foodItem.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerDragStart(marker: Marker) {

    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
}