package org.wit.myfooddiary.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.myfooddiary.R
import org.wit.myfooddiary.adapters.FoodItemListener
import org.wit.myfooddiary.adapters.MyFoodDiaryAdapter
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.databinding.FragmentMyFoodListBinding
import org.wit.myfooddiary.main.MainApp
import org.wit.myfooddiary.models.FoodItemJSONStore
import org.wit.myfooddiary.models.FoodItemStore
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.UserModel

class MyFoodListFragment : Fragment(), FoodItemListener {
    private var _fragBinding: FragmentMyFoodListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    var user = UserModel()
    lateinit var app: MainApp

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
        activity?.title = getString(R.string.action_myfoodlist)
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = MyFoodDiaryAdapter(app.foodItems.findAll(), this )
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyFoodListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
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