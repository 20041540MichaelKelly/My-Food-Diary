package org.wit.myfooddiary.ui.individualfooditem

import android.net.Uri
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
import org.wit.myfooddiary.databinding.FragmentMyFoodListBinding
import org.wit.myfooddiary.databinding.IndividualFoodItemFragmentBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IndividualFoodItemFragment : Fragment() {
    private var _fragBinding: IndividualFoodItemFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    private val args by navArgs<IndividualFoodItemFragmentArgs>()
    private lateinit var individualFoodItemViewModel: IndividualFoodItemViewModel
    var foodItem = FoodModel()
    lateinit var myFoodListViewModel: MyFoodListViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private lateinit var usedForUpdateFoodItem : FoodModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
               // myFoodListViewModel.getCordinates()
                myFoodListViewModel.observableFoodItemsList.observe(
                    viewLifecycleOwner, Observer { foodItems ->
                        foodItems?.let {
                            foodItems.forEach { foodItem ->
                                    if (foodItem.timeForFood == args.foodid.toString()) {  //Im using timeFor Food which is a timeStamp as the id for clicking individual items
                                        getTheFood(foodItem)
                                    }
                            }
                        }
                    })
                }
            })
        setButtonOnClickListeners(fragBinding)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        individualFoodItemViewModel = ViewModelProvider(this).get(IndividualFoodItemViewModel::class.java)
    }

    private fun getTheFood(foodItem: FoodModel) {
        usedForUpdateFoodItem = foodItem
        fragBinding.editTitle.setText(foodItem.title)

        fragBinding.editDescription.setText(foodItem.description)
        fragBinding.editAmountOfCals.setText(foodItem.amountOfCals.toString())
        fragBinding.image.setImageURI(Uri.parse(foodItem.image))
    }

   private fun setButtonOnClickListeners(
       layout: IndividualFoodItemFragmentBinding
   ){
       layout.editFoodItemButton.setOnClickListener(){
           val foodid = usedForUpdateFoodItem.fid
           usedForUpdateFoodItem.title= layout.editTitle.text.toString()
           usedForUpdateFoodItem.description = layout.editDescription.text.toString()
           usedForUpdateFoodItem.amountOfCals = layout.editAmountOfCals.text.toString().toInt()

           if (foodid != null) {
               myFoodListViewModel.updateFoodItem(loggedInViewModel.liveFirebaseUser,
                   foodid, usedForUpdateFoodItem)
           }
       }

       layout.deleteFoodItemButton.setOnClickListener(){
           myFoodListViewModel.deleteItem(loggedInViewModel.liveFirebaseUser,
               foodItem)
            Toast.makeText(context, "Food Item Deleted", Toast.LENGTH_LONG).show()

       }
   }



}