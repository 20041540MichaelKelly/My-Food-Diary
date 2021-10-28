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

const val JSON_FILE = "fooditemsandusers.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<FoodModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FoodItemJSONStore(private val context: Context) : FoodItemStore {

    var foodItems = mutableListOf<FoodModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FoodModel> {
        logAll()
        return foodItems
    }

    override fun create(foodItem: FoodModel) {
        foodItem.id = generateRandomId()
        foodItems.add(foodItem)
        serialize()

    }

    override fun delete(foodItem: FoodModel) {
        foodItems.remove(foodItem)
        serialize()
    }


    override fun update(foodItem: FoodModel) {
        // todo
    }

    override fun findAllById(id: Long): List<FoodModel> {
        val iList = ArrayList<FoodModel>()
        for(f in foodItems)
            if(f.Uid == id){
                iList.add(f)
            }

        return iList

    }

    override fun findAllUsers(): List<FoodModel> {
        TODO("Not yet implemented")
    }

    override fun findOneUser(id: Long): FoodModel? {
        var foundUser: FoodModel? = foodItems.find { p -> p.id == id }
        return foundUser
    }

    override fun createUser(foodItem: FoodModel) {
        foodItem.Uid = generateRandomId()
        foodItems.add(foodItem)
        serialize()
    }


    override fun updateUser(foodItem: FoodModel) {
        // todo
    }

    override fun checkCredientials(foodItem: FoodModel): FoodModel? {
        var foundUser: FoodModel? = foodItems.find { p ->
            p.password == foodItem.password &&
                    p.email == foodItem.email
        }

        return foundUser

    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(foodItems, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        foodItems = gsonBuilder.fromJson(jsonString, listType)
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