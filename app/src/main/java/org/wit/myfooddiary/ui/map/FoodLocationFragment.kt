package org.wit.myfooddiary.ui.map

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FoodMapBinding
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListViewModel
import org.wit.myfooddiary.utils.hideLoader

class FoodLocationFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private var _fragBinding: FoodMapBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var map: GoogleMap
    private lateinit var foodLocationViewModel: FoodLocationViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FoodMapBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        foodLocationViewModel = ViewModelProvider(this).get(FoodLocationViewModel::class.java)

        fragBinding.mapView.onCreate(savedInstanceState)
        fragBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        fragBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        fragBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        fragBinding.mapView.onPause()
    }

    fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        foodLocationViewModel.observableFoodLocationList.observe(
            viewLifecycleOwner,
            Observer { foodItems ->
                foodItems?.forEach {
                    val loc = LatLng(it.lat, it.lng)
                    val options = MarkerOptions().title(it.title).position(loc)
                    map.addMarker(options)?.tag = it.fid
                    map.setOnMarkerClickListener(this)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))

                }
            })
    }




    override fun onResume() {
        super.onResume()
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                foodLocationViewModel.liveFirebaseUser.value = firebaseUser
                foodLocationViewModel.load()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragBinding.mapView.onSaveInstanceState(outState)
    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        fragBinding.currentTitle.text = marker.title

        return false
    }
}
