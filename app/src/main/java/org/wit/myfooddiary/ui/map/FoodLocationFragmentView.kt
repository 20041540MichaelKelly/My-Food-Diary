package org.wit.myfooddiary.ui.map

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
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
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FoodMapBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
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
    private lateinit var foodLocationViewModel: FoodLocationViewModel
    lateinit var myFoodListViewModel: MyFoodListViewModel
    private val args by navArgs<IndividualFoodItemFragmentArgs>()
    lateinit var loader : AlertDialog

    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private lateinit var presenter: FoodLocationFragmentPresenter


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
        foodLocationViewModel = ViewModelProvider(this).get(FoodLocationViewModel::class.java)
        myFoodListViewModel = ViewModelProvider(this).get(MyFoodListViewModel::class.java)
        presenter = FoodLocationFragmentPresenter(this)

        loader = createLoader(requireActivity())
//        fragBinding.title = getString(R.string.action_myfoodlist)
        myFoodListViewModel = ViewModelProvider(this).get(MyFoodListViewModel::class.java)
        showLoader(loader,"Downloading Food")
        myFoodListViewModel.observableFoodItemsList.observe(viewLifecycleOwner, Observer {
                foodItems ->
            foodItems?.let {
               // render(foodItems)
                hideLoader(loader)
            }
        })

        return root
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }

    fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
//        myFoodListViewModel.observableFoodItemsList.observe(viewLifecycleOwner, Observer {
//                foodItems ->
//            foodItems?.let{
//                getLocations(foodItems)
//                }
//            })
        foodLocationViewModel.observableIndividualFoodItem.observe(viewLifecycleOwner, Observer {foodItem ->
        getLocations(foodItem)
        })
    }

    private fun getLocations(foodItem: FoodModel) {

        val loc = LatLng(foodItem.lat, foodItem.lng)
        val options = MarkerOptions().title(foodItem.title).position(loc)
        map.addMarker(options)?.tag = foodItem.fid
        map.setOnMarkerClickListener(this)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, foodItem.zoom))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
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

    fun showLocations(foodItem: FoodModel) {
        fragBinding.currentTitle.setText(foodItem.title)
        fragBinding.currentDescription.setText(foodItem.description)
//
//        Picasso.get()
//            .load(foodItem.image)
//            .into(fragBinding.foodView)
//        if (foodItem.image != "") {
//            fragBinding.foodView.setText(R.string.change_food_image)
//        }

    }
}
