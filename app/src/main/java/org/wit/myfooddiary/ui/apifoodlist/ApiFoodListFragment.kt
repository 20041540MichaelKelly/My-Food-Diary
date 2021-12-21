package org.wit.myfooddiary.ui.apifoodlist

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.myfooddiary.R
import org.wit.myfooddiary.adapters.FoodItemListener
import org.wit.myfooddiary.adapters.MyFoodDiaryAdapter
import org.wit.myfooddiary.databinding.FragmentApiMyFoodListBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.utils.createLoader
import org.wit.myfooddiary.utils.hideLoader
import org.wit.myfooddiary.utils.showLoader


class ApiFoodListFragment : Fragment(), FoodItemListener {
    private var _fragBinding: FragmentApiMyFoodListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    lateinit var loader : AlertDialog

    private lateinit var apiFoodListViewModel: ApiFoodListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentApiMyFoodListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        loader = createLoader(requireActivity())
        apiFoodListViewModel = ViewModelProvider(this).get(ApiFoodListViewModel::class.java)
        showLoader(loader,"Downloading Food")
        apiFoodListViewModel.observableApiFoodItemsList.observe(viewLifecycleOwner, Observer {
                foodItems ->
                foodItems?.let {
                render(foodItems)
                hideLoader(loader)
            }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
//            val action = MyFoodListFragmentDirections.actionMyFoodListFragmentToMyFoodDiaryFragment()
//            findNavController().navigate(action)
        }
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(foodItems: List<FoodModel>) {
        fragBinding.recyclerView.adapter = MyFoodDiaryAdapter(foodItems,this)
        if (foodItems.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.foodItemsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.foodItemsNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        apiFoodListViewModel.load()
//        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
//            if (firebaseUser != null) {
//                apiFoodListViewModel.liveFirebaseUser.value = firebaseUser
//                apiFoodListViewModel.load()
//            }
        //})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onFoodItemClick(foodItem: FoodModel) {
//        val action = MyFoodListFragmentDirections.actionMyFoodListFragmentToIndividualFoodItemFragment(
//            foodItem.fid!!.toLong())
//        findNavController().navigate(action)
    }

    override fun onFoodItemDelete(foodItem: FoodModel) {
        TODO("Not yet implemented")
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Food Items")
            //Retrieve food List again here

        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }
}