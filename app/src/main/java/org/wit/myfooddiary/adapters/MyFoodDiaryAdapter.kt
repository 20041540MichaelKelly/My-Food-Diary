package org.wit.myfooddiary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.CardFoodBinding
import org.wit.myfooddiary.models.FoodModel

interface FoodItemListener {
    fun onFoodItemClick(foodItem: FoodModel)
    fun onFoodItemDelete(foodItem: FoodModel)
}

class MyFoodDiaryAdapter constructor(private var foodItems: List<FoodModel>,
                                     private val listener: FoodItemListener) :
    RecyclerView.Adapter<MyFoodDiaryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFoodBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val foodItem = foodItems[holder.adapterPosition]
        holder.bind(foodItem, listener)
    }

    override fun getItemCount(): Int = foodItems.size

//    fun findIndex(foodItems: List<FoodModel>, foodItem: FoodModel): Int {
//        return foodItems.indexOf(foodItem)
//    }


    class MainHolder (private val binding : CardFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        fun findIndex(foodItems: List<FoodModel>, foodItem: FoodModel): Int {
//            return foodItems.indexOf(foodItem)
//        }
        fun bind(foodItem: FoodModel, listener: FoodItemListener) {

            binding.foodTitle.text = foodItem.title
            binding.description.text = foodItem.description
            Picasso.get().load(foodItem.image).resize(200, 200).into(binding.imageIcon)
            binding.actionRemove.setOnClickListener {
                Snackbar.make(it, R.string.deleted_foodItem, Snackbar.LENGTH_LONG)
                    .show()
                listener.onFoodItemDelete(foodItem)
//                val iIndexOfFoodItem = findIndex(foodItems, foodItem)
//               foodItems.drop(iIndexOfFoodItem)
                true
            }
            binding.root.setOnClickListener {
                listener.onFoodItemClick(foodItem)
            }
        }
    }
}