package org.wit.myfooddiary.ui.apifoodlist

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.wit.myfooddiary.R
import org.wit.myfooddiary.adapters.FoodItemListener
import org.wit.myfooddiary.adapters.MyFoodDiaryAdapter
import org.wit.myfooddiary.databinding.FragmentApiMyFoodListBinding
import org.wit.myfooddiary.databinding.FragmentMyFoodDiaryBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.ui.fooddiary.MyFoodDiaryViewModel
import org.wit.myfooddiary.ui.foodlist.MyFoodListViewModel
import org.wit.myfooddiary.utils.createLoader
import org.wit.myfooddiary.utils.hideLoader
import org.wit.myfooddiary.utils.showLoader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ApiFoodListFragment : Fragment(), FoodItemListener {
    private var _fragBinding: FragmentApiMyFoodListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    lateinit var loader: AlertDialog

    private lateinit var apiFoodListViewModel: ApiFoodListViewModel
    private lateinit var myFoodListViewModel: MyFoodListViewModel
    lateinit var myFoodDiaryViewModel: MyFoodDiaryViewModel

    private val loggedInViewModel: LoggedInViewModel by activityViewModels()


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
        showLoader(loader, "Downloading Food")
        apiFoodListViewModel.observableApiFoodItemsList.observe(
            viewLifecycleOwner,
            Observer { foodItems ->
                foodItems?.let {
                    render(foodItems)
                    hideLoader(loader)
                }
            })
        myFoodListViewModel = ViewModelProvider(this).get(MyFoodListViewModel::class.java)
        myFoodDiaryViewModel = ViewModelProvider(this).get(MyFoodDiaryViewModel::class.java)


        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
//            val action = MyFoodListFragmentDirections.actionMyFoodListFragmentToMyFoodDiaryFragment()
//            findNavController().navigate(action)
        }
        setButtonListener(fragBinding)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onFoodItemClick(foodItem: FoodModel) {
        addToFirebaseDB(foodItem)
    }

    private fun addToFirebaseDB(foodItem: FoodModel) {
        myFoodDiaryViewModel.addFoodItem(
            loggedInViewModel.liveFirebaseUser,
            FoodModel(
                title = foodItem.title,
                description = foodItem.description,
                amountOfCals = foodItem.amountOfCals,
                dateLogged = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("M/d/y H:m:ss")),
                timeForFood = System.currentTimeMillis().toString(),
                image = foodItem.image,
                email = loggedInViewModel.liveFirebaseUser.value?.email!!,
                lat = foodItem.lat,
                lng = foodItem.lng,
                zoom = 15f
            )
        )
        Toast.makeText(context,"Food Item added",Toast.LENGTH_LONG).show()

    }

    override fun onFoodItemDelete(foodItem: FoodModel) {
        TODO("Not yet implemented")
    }


    fun setButtonListener(
        layout: FragmentApiMyFoodListBinding
    ){

    layout.seekBar.setOnSeekBarChangeListener(object :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seek: SeekBar,
                                       progress: Int, fromUser: Boolean) {
            //dont need these just had to implement them, to solve error
        }

        override fun onStartTrackingTouch(seek: SeekBar) {
            //same as above
        }

        override fun onStopTrackingTouch(seek: SeekBar) {
            // write custom code for progress is stopped
            Toast.makeText(context,"Amount of calories : ${seek.progress}",Toast.LENGTH_LONG).show()

        }
    })

        layout.filterBtn.setOnClickListener() {
            val amtOfItems = layout.editAmount.text.toString()
            val seekBarAmt= layout.seekBar.progress.toString()

            apiFoodListViewModel.filterApi(amtOfItems, seekBarAmt)
            Toast.makeText(context,"Food ideas returned : ${amtOfItems} with a max cal of " +
                    "${seekBarAmt}",Toast.LENGTH_LONG).show()

        }

    }
}