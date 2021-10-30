package org.wit.myfooddiary.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.myfooddiary.helpers.exists
import org.wit.myfooddiary.helpers.read
import org.wit.myfooddiary.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE_FOOD = "preciousFood.json"
val gsonBuilderFood: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listTypeFood: Type = object : TypeToken<ArrayList<FoodModel>>() {}.type



fun generateRandomId(): Long {
    return Random().nextLong()
}

class FoodItemJSONStore(private val context: Context) : FoodItemStore {


    var foodItems = mutableListOf<FoodModel>()
    var users = mutableListOf<UserModel>()


    init {
        if (exists(context, JSON_FILE_FOOD)) {
            deserialize()

        }
    }

    override fun findAll(): MutableList<FoodModel> {
        logAll()
        return foodItems
    }

    override fun create(foodItem: FoodModel, user: UserModel) {
        foodItem.id = generateRandomId()
        //foodItem.fUid = user.Uid
        user.foodObject.add(foodItem )
        foodItems.add(foodItem)
        serialize()

    }

    override fun removeItem(foodItem: FoodModel) {
        foodItem.id = 0L
        foodItem.title = ""
        foodItem.description = ""
        foodItem.image = Uri.EMPTY
        foodItem.lat= 0.0
        foodItem.lng= 0.0
        foodItem.zoom = 0f
        serialize()

    }

    override fun deleteItem(foodItem: FoodModel) {
        foodItems.remove(foodItem)
        serialize()
    }


    override fun update(foodItem: FoodModel) {
        val foodItemList = findAll() as ArrayList<FoodModel>
        var foundFoundItem: FoodModel? = foodItemList.find { p -> p.id == foodItem.id }
        if (foundFoundItem != null) {
            foundFoundItem.fUid = foodItem.fUid
            foundFoundItem.title = foodItem.title
            foundFoundItem.description = foodItem.description
            foundFoundItem.amountOfCals = foodItem.amountOfCals
            foundFoundItem.image = foodItem.image
            foundFoundItem.lat = foodItem.lat
            foundFoundItem.lng = foodItem.lng
            foundFoundItem.zoom = foodItem.zoom
        }
        serialize()
    }



    override fun findAllById(id: Long): List<FoodModel> {
        val iList = ArrayList<FoodModel>()
        for(f in foodItems)
            if(f.fUid == id){
                iList.add(f)
            }
        return iList

    }


    private fun serialize() {
        val jsonString = gsonBuilderFood.toJson(foodItems, listTypeFood)
        write(context, JSON_FILE_FOOD, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_FOOD)
        foodItems = gsonBuilderFood.fromJson(jsonString, listTypeFood)
    }

    private fun logAll() {
        foodItems.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }

}