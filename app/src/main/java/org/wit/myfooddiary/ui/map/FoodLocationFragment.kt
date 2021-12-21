package org.wit.myfooddiary.ui.map

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.maps.GoogleMap
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FoodMapBinding

class FoodLocationFragment : Fragment() {

    private var _fragBinding: FoodMapBinding? = null
    private val fragBinding get() = _fragBinding!!
    lateinit var map: GoogleMap

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

        fragBinding.mapView.onCreate(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        fragBinding.mapView.onResume()
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
}
