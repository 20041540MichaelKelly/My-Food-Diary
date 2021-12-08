package org.wit.myfooddiary.ui.individualfooditem

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.wit.myfooddiary.R

class IndividualFoodItemFragment : Fragment() {

    companion object {
        fun newInstance() = IndividualFoodItemFragment()
    }

    private lateinit var viewModel: IndividualFoodItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.individual_food_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IndividualFoodItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

}