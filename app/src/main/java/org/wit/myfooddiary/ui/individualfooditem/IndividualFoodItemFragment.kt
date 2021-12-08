package org.wit.myfooddiary.ui.individualfooditem

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.databinding.FragmentMyFoodListBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListViewModel

class IndividualFoodItemFragment : Fragment() {

    private val args by navArgs<IndividualFoodItemFragmentArgs>()
    private lateinit var individualFoodItemViewModel: IndividualFoodItemViewModel
    var foodItem = FoodModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Toast.makeText(context,"Food Item ID Selected : ${args.fooditemid}",Toast.LENGTH_LONG).show()
        individualFoodItemViewModel = ViewModelProvider(this).get(IndividualFoodItemViewModel::class.java)
        individualFoodItemViewModel.getFoodItem(foodItem.id)

        return view    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        individualFoodItemViewModel = ViewModelProvider(this).get(IndividualFoodItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}