package org.wit.myfooddiary.ui.foodlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import org.wit.myfooddiary.databinding.FragmentMyFoodListBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.UserModel

class MyFoodListFragment : Fragment(), FoodItemListener {
    private var _fragBinding: FragmentMyFoodListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    var user = UserModel()
    lateinit var app: MainApp
    private lateinit var myFoodListViewModel: MyFoodListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMyFoodListBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

       // activity?.title = getString(R.string.action_myfoodlist)
        myFoodListViewModel = ViewModelProvider(this).get(MyFoodListViewModel::class.java)
        myFoodListViewModel.observableDonationsList.observe(viewLifecycleOwner, Observer {
                foodItems ->
            foodItems?.let { render(foodItems) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = MyFoodListFragmentDirections.actionMyFoodListFragmentToMyFoodDiaryFragment()
            findNavController().navigate(action)
        }
        return root
    }

//    companion object {
//        @JvmStatic
//        fun newInstance() =
//            MyFoodListFragment().apply {
//                arguments = Bundle().apply {
//                }
//            }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(foodList: List<FoodModel>) {
        fragBinding.recyclerView.adapter = MyFoodDiaryAdapter(foodList, this)
        if (foodList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
           // fragBinding.foodItemsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            //fragBinding.donationsNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        myFoodListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onFoodItemClick(foodItem: FoodModel) {
        TODO("Not yet implemented")
    }

    override fun onFoodItemDelete(foodItem: FoodModel) {
        TODO("Not yet implemented")
    }
}