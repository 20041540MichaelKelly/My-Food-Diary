package org.wit.myfooddiary.adapters

import android.net.Uri
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
        //holder.bind(foodItem)
    }

    override fun getItemCount(): Int = foodItems.size

    class MainHolder (private val binding : CardFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodItem: FoodModel, listener: FoodItemListener) {
            //binding.paymentamount.text = donation.amount.toString()
            //binding.paymentmethod.text = donation.paymentmethod

            binding.foodItem = foodItem
            binding.imageIcon.setImageURI(Uri.parse(foodItem.image))
//            if(foodItem.image == Uri.EMPTY){
//                Picasso.get().load("content://com.android.providers.media.documents/document/image%3A5589").resize(200, 200).into(binding.imageIcon)
//            }else{

            if(foodItem.image == ""){
                Picasso.get().load("content://com.android.providers.media.documents/document/image%3A5589").resize(200, 200).into(binding.imageIcon)

            }else{
                Picasso.get().load(foodItem.image).resize(200, 200).into(binding.imageIcon)
            }

            binding.root.setOnClickListener { listener.onFoodItemClick(foodItem)}
            //Include this call to force the bindings to happen immediately
            binding.executePendingBindings()
        }

//        fun bind(foodItem: FoodModel, listener: FoodItemListener) {
//            val aCals = foodItem.amountOfCals.toString() +"ckals"
//            binding.foodTitle.text = foodItem.title
//            binding.description.text = foodItem.description
//            if(foodItem.image == Uri.EMPTY){
//                Picasso.get().load("content://com.android.providers.media.documents/document/image%3A5589").resize(200, 200).into(binding.imageIcon)
//            }else{
//                Picasso.get().load(foodItem.image).resize(200, 200).into(binding.imageIcon)
//            }
//            binding.displayCals.text = aCals
//            binding.dateTime.text = foodItem.timeForFood
//            binding.actionRemove.setOnClickListener {
//                Snackbar.make(it, R.string.deleted_foodItem, Snackbar.LENGTH_LONG)
//                    .show()
//                listener.onFoodItemDelete(foodItem)
//                true
//            }
//            binding.root.setOnClickListener {
//                listener.onFoodItemClick(foodItem)
//            }
//        }
    }


}