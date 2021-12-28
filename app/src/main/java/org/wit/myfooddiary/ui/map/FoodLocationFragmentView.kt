package org.wit.myfooddiary.ui.map

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FoodMapBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListFragment
import org.wit.myfooddiary.ui.foodlist.MyFoodListViewModel
import org.wit.myfooddiary.ui.individualfooditem.IndividualFoodItemFragmentArgs
import org.wit.myfooddiary.utils.createLoader
import org.wit.myfooddiary.utils.hideLoader
import org.wit.myfooddiary.utils.showLoader

class FoodLocationFragmentView: Fragment(),
    GoogleMap.OnMarkerClickListener {
    private var _fragBinding: FoodMapBinding? = null
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    lateinit var map: GoogleMap
    private val myFoodListFragment : MyFoodListFragment ?= null
    private lateinit var foodLocationViewModel: FoodLocationViewModel
    lateinit var myFoodListViewModel: MyFoodListViewModel
    lateinit var loader : AlertDialog
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private lateinit var presenter: FoodLocationFragmentPresenter
    private lateinit var listNeeded: List<FoodModel>

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
            //  FoodLocationViewModel.getFoodItem(foodItem.uid)
        fragBinding.mapView.onCreate(savedInstanceState);
        fragBinding.mapView.getMapAsync{
            map = it
            presenter.doConfigureMap(map)
        }
       // foodLocationViewModel = ViewModelProvider(this).get(FoodLocationViewModel::class.java)
        myFoodListViewModel = ViewModelProvider(this).get(MyFoodListViewModel::class.java)
        presenter = FoodLocationFragmentPresenter(this)

        loader = createLoader(requireActivity())

        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                myFoodListViewModel.liveFirebaseUser.value = firebaseUser
                myFoodListViewModel.load()
                myFoodListViewModel.getCordinates()
                myFoodListViewModel.observableFoodItemsList.observe(
                    viewLifecycleOwner, Observer { foodItems ->
                        foodItems?.let {
                            getLocations(foodItems)
                        }
                    })
            }
        })

        return root
    }


    private fun getLocations(foodItems: List<FoodModel>) {
        listNeeded = foodItems
        foodItems.forEach {
                foodItem ->
            if(foodItem.lat != 0.0 && foodItem.lng != 0.0) {
                map.uiSettings.setZoomControlsEnabled(true)
                val loc = LatLng(foodItem.lat, foodItem.lng)
                val options = MarkerOptions()
                    .title(foodItem.title)
                    .position(loc)
                map.addMarker(options)?.tag = foodItem.fid
                map.setOnMarkerClickListener(this)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, foodItem.zoom))
            }
        }
    }

     override fun onMarkerClick(marker: Marker): Boolean {
        listNeeded.forEach { ln ->
            val locs = LatLng(ln.lat, ln.lng)
            if(ln.title == marker.title) {
                marker.snippet = "GPS : $locs"

                showLocations(ln)
            }
        }

        return false
    }

    fun showLocations(foodItem: FoodModel) {

        fragBinding.currentTitle.setText(foodItem.title)
        fragBinding.currentDescription.setText(foodItem.description)
        if(foodItem.image != "") {
            Picasso.get()
                .load(foodItem.image)
                .resize(200, 200)
                .rotate(90F)
                .into(fragBinding.foodView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
        val item = menu.findItem(R.id.toggleFoodItems) as MenuItem
        item.setActionView(R.layout.togglebutton_layout)
        val toggleFoodItems: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleFoodItems.isChecked = false

        toggleFoodItems.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) myFoodListViewModel.loadAll()
            else myFoodListViewModel.load()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
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

    override fun onResume() {
        super.onResume()
        fragBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragBinding.mapView.onSaveInstanceState(outState)
    }


}
