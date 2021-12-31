package org.wit.myfooddiary.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.R
import org.wit.myfooddiary.databinding.ActivityFoodListBinding.inflate
import org.wit.myfooddiary.databinding.CardFoodBinding
import org.wit.myfooddiary.models.FoodModel
import org.wit.myfooddiary.ui.map.FoodLocationViewModel

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

    inner class MainHolder ( private val binding : CardFoodBinding ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodItem: FoodModel, listener: FoodItemListener) {
            binding.foodItem = foodItem
            binding.imageIcon.setImageURI(Uri.parse(foodItem.image))
            if(foodItem.image.contains("content://org.wit.myfooddiary")){
                Picasso.get().load(foodItem.image).resize(200, 200)
                    .rotate(90F).into(binding.imageIcon)
            } else if(foodItem.image == ""){
                Picasso.get().load("content://com.android.providers.media.documents/document/image%3A11631").resize(200, 200).into(binding.imageIcon)
            }else{
                Picasso.get().load(foodItem.image).resize(200, 200).into(binding.imageIcon)
            }
            binding.actionRemove.setOnClickListener {
                listener.onFoodItemDelete(foodItem)

                Snackbar.make(it, R.string.deleted_foodItem, Snackbar.LENGTH_LONG)
                    .show()
            }
            binding.root.setOnClickListener {
                listener.onFoodItemClick(foodItem)
            }
            //Include this call to force the bindings to happen immediately
            binding.executePendingBindings()
        }

    }


}