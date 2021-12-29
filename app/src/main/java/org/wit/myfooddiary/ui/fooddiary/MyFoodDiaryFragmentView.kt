package org.wit.myfooddiary.ui.fooddiary

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.adapters.FoodItemListener
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.models.UserModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListFragmentDirections
import timber.log.Timber.i

class MyFoodDiaryFragmentView : Fragment(), FoodItemListener {
    private var _fragBinding: FragmentMyFoodDiaryBinding? = null
    val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    var user = UserModel()
    lateinit var myFoodDiaryViewModel: MyFoodDiaryViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    var edit = false
    private lateinit var presenter: MyFoodDiaryFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMyFoodDiaryBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        // activity?.title = getString(R.string.action_myfooddiary)
        presenter = MyFoodDiaryFragmentPresenter(this)

        fragBinding.chooseImage.setOnClickListener {
            presenter.cacheFooodLocation(fragBinding.foodTitle.text.toString(), fragBinding.description.text.toString())
            presenter.doSelectImage()
        }

        myFoodDiaryViewModel = ViewModelProvider(this).get(MyFoodDiaryViewModel::class.java)
        myFoodDiaryViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { render(status) }
        })
        fragBinding.amountOfCals.minValue = 1
        fragBinding.amountOfCals.maxValue = 1000

        fragBinding.amountOfCals.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            // donateLayout.paymentAmount.setText("$newVal")
            foodItem.amountOfCals = newVal
        }

        presenter.setButtonListener(fragBinding, loggedInViewModel)

        return root
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context, getString(R.string.foodItemError), Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfooddiary, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(fragBinding.foodImage)
        fragBinding.chooseImage.setText(R.string.change_food_image)
    }

     fun foodItemLoc(foodItem : FoodModel) {
         //if(foodItem.fid != ""){
        val action = MyFoodDiaryFragmentViewDirections.actionMyFoodDiaryFragmentToFoodLocation()
           //foodItem.fid!!.toLong())
        findNavController().navigate(action)
    }

    override fun onFoodItemClick(foodItem: FoodModel) {
        val action = MyFoodListFragmentDirections.actionMyFoodListFragmentToIndividualFoodItemFragment(foodItem.fid!!.toLong())
        findNavController().navigate(action)
    }

    override fun onFoodItemDelete(foodItem: FoodModel) {
        TODO("Not yet implemented")
    }


}