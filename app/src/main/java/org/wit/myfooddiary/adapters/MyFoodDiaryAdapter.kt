package org.wit.myfooddiary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.myfooddiary.databinding.CardFoodBinding
import org.wit.myfooddiary.models.FoodModel

interface FoodItemListener {
    fun onFoodItemClick(foodItem: FoodModel)
}

class MyFoodDiaryAdapter constructor(private var foodItems: List<FoodModel>, private val listener: FoodItemListener) :
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

    class MainHolder(private val binding : CardFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fundItem: FoodModel) {
            binding.foodTitle.text = fundItem.title
            binding.description.text = fundItem.description
            binding.root.setOnClickListener { listener.onFoodItemClick(foodItem) }
        }
    }
}