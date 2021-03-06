package org.wit.myfooddiary.ui.foodlist

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
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
import org.wit.myfooddiary.databinding.FragmentMyFoodListBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.auth.LoggedInViewModel
import org.wit.myfooddiary.utils.createLoader
import org.wit.myfooddiary.utils.hideLoader
import org.wit.myfooddiary.utils.showLoader


class MyFoodListFragment : Fragment(), FoodItemListener {
    private var _fragBinding: FragmentMyFoodListBinding? = null
    private val fragBinding get() = _fragBinding!!
    var foodItem = FoodModel()
    lateinit var loader : AlertDialog
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private lateinit var myFoodListViewModel: MyFoodListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMyFoodListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        loader = createLoader(requireActivity())
//        fragBinding.title = getString(R.string.action_myfoodlist)
        myFoodListViewModel = ViewModelProvider(this).get(MyFoodListViewModel::class.java)
        showLoader(loader,"Downloading Food")
        myFoodListViewModel.observableFoodItemsList.observe(viewLifecycleOwner, Observer<List<FoodModel>> {
                foodItems ->
                foodItems?.let {
                render(foodItems)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })
        setSwipeRefresh()
        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = MyFoodListFragmentDirections.actionMyFoodListFragmentToMyFoodDiaryFragment()
            findNavController().navigate(action)
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_myfoodlist, menu)
        val searchItem=menu.findItem(R.id.action_search)
        val searchView=searchItem?.actionView as SearchView;
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val localLi = ArrayList<FoodModel>()
                myFoodListViewModel.observableFoodItemsList.observe(
                    viewLifecycleOwner,
                    Observer<List<FoodModel>> { foodItems ->
                        foodItems.forEach { foodItem ->
                            if (query!!.lowercase() in foodItem.title.lowercase()) {
                                localLi.add(foodItem)
                                checkSwipeRefresh()
                            }

                        }
                        showFoodItems(localLi)
                    })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        val item = menu.findItem(R.id.toggleFoodItems) as MenuItem
        item.setActionView(R.layout.togglebutton_layout)
        val toggleFoodItems: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleFoodItems.isChecked = false

        toggleFoodItems.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) myFoodListViewModel.loadAll()
            else myFoodListViewModel.load()
        }
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
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                myFoodListViewModel.liveFirebaseUser.value = firebaseUser
                myFoodListViewModel.load()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onFoodItemClick(foodItem: FoodModel) {
        if(loggedInViewModel.liveFirebaseUser.value!!.uid == foodItem.uid) {
            val action =
                MyFoodListFragmentDirections.actionMyFoodListFragmentToIndividualFoodItemFragment(
                    foodItem.timeForFood.toLong()
                )
            findNavController().navigate(action)
        }
    }

    override fun onFoodItemDelete(foodItem: FoodModel) {

//        myFoodListViewModel.deleteItem(loggedInViewModel.liveFirebaseUser,
//            foodItem)
//        val ans = myFoodListViewModel.observableFoodItemsList.value
//
//        Handler().postDelayed({
//            if (ans != null) {
//                showFoodItems(ans)
//            }
//        }, 1500)
    }

    private fun loadFoodItems() {
        val ans = myFoodListViewModel.observableFoodItemsList.value

        if (ans != null) {
            render(ans)
        }

    }

    fun showFoodItems (foodItems: List<FoodModel>) {
        fragBinding.recyclerView.adapter = MyFoodDiaryAdapter(foodItems, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }

    fun listOfFoodItems() {
        showLoader(loader, "Downloading Food")
        myFoodListViewModel.observableFoodItemsList.observe(
            viewLifecycleOwner,
            Observer<List<FoodModel>> { foodItems ->
                foodItems?.let {
                    render(foodItems)
                    hideLoader(loader)
                    checkSwipeRefresh()

                }
            })
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Food")
            myFoodListViewModel.observableFoodItemsList.observe(
                viewLifecycleOwner,
                Observer<List<FoodModel>> { foodItems ->
                    foodItems?.let {
                        render(foodItems)
                        hideLoader(loader)
                        checkSwipeRefresh()

                    }
                })
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

}
