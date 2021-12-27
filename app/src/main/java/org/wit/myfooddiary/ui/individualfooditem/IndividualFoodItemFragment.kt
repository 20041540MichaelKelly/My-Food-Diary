package org.wit.myfooddiary.ui.individualfooditem

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.databinding.FragmentMyFoodListBinding
import org.wit.myfooddiary.databinding.IndividualFoodItemFragmentBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListViewModel

class IndividualFoodItemFragment : Fragment() {
    private var _fragBinding: IndividualFoodItemFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    private val args by navArgs<IndividualFoodItemFragmentArgs>()
    private lateinit var individualFoodItemViewModel: IndividualFoodItemViewModel
    var foodItem = FoodModel()
    lateinit var myFoodListViewModel: MyFoodListViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = IndividualFoodItemFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        Toast.makeText(context,"Food Item ID Selected : ${args.foodid}",Toast.LENGTH_LONG).show()

        myFoodListViewModel = ViewModelProvider(this).get(MyFoodListViewModel::class.java)

        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                myFoodListViewModel.liveFirebaseUser.value = firebaseUser
                myFoodListViewModel.load()
                myFoodListViewModel.getCordinates()
                myFoodListViewModel.observableFoodItemsList.observe(
                    viewLifecycleOwner, Observer { foodItems ->
                        foodItems?.let {
                            foodItems.forEach {foodItem ->
                                if(foodItem.timeForFood == args.foodid.toString()) {
                                    getTheFood(foodItem)
                                }
                            }
                        }
                    })
            }
        })





        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        individualFoodItemViewModel = ViewModelProvider(this).get(IndividualFoodItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun getTheFood(foodItem: FoodModel) {
        fragBinding.editTitle.setText(foodItem.title)

        fragBinding.editDescription.setText(foodItem.description)
    }

}