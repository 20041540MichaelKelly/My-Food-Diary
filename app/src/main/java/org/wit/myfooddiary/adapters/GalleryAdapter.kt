package org.wit.myfooddiary.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.myfooddiary.activities.GalleryActivity
import org.wit.myfooddiary.databinding.CardGalleryBinding
import org.wit.myfooddiary.models.FoodModel


class GalleryAdapter(private var foodItems: List<FoodModel>,
                     private val listener: FoodItemListener
) :
    RecyclerView.Adapter<GalleryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGalleryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val foodItem = foodItems[holder.adapterPosition]
        holder.bind(foodItem, listener)
    }

    override fun getItemCount(): Int = foodItems.size

    class MainHolder (private val binding : CardGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodItem: FoodModel, listener: FoodItemListener) {
            if(foodItem.image != Uri.EMPTY) {
                Picasso.get().load(foodItem.image).resize(600, 600).into(binding.images)
            }
            binding.root.setOnClickListener {
                listener.onFoodItemClick(foodItem)
            }
        }
    }
}