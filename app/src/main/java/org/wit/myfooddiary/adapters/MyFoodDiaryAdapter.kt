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
                                     private val listener: FoodItemListener,
                                     private val readOnly: Boolean) :
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

    inner class MainHolder ( private val binding : CardFoodBinding ) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly


        fun bind(foodItem: FoodModel, listener: FoodItemListener) {
            binding.foodItem = foodItem
            binding.imageIcon.setImageURI(Uri.parse(foodItem.image))
            if(foodItem.image.contains("content://org.wit.myfooddiary")){
                Picasso.get().load(foodItem.image).resize(200, 200)
                    .rotate(90F).into(binding.imageIcon)
            } else if(foodItem.image == ""){
                Picasso.get().load("@drawable/ic_baseline_food_bank_24.xml").resize(200, 200).into(binding.imageIcon)
            }else{
                Picasso.get().load(foodItem.image).resize(200, 200).rotate(90F).into(binding.imageIcon)
            }

            binding.root.setOnClickListener { listener.onFoodItemClick(foodItem)}
            //Include this call to force the bindings to happen immediately
            binding.executePendingBindings()
        }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val charSearch = constraint.toString()
                    if (charSearch.isEmpty()) {
                        foodItems = foodItems as ArrayList<FoodModel>
                    } else {
                        val resultList = ArrayList<FoodModel>()
                        for (row in foodItems) {
                            if (row.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                                resultList.add(row)
                            }
                        }
                        foodItems = resultList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = foodItems
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    foodItems = results?.values as ArrayList<FoodModel>
                    notifyDataSetChanged()
                }
            }
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