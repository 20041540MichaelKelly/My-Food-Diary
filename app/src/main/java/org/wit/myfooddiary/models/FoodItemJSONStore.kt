package org.wit.myfooddiary.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.merge
import org.wit.myfooddiary.helpers.exists
import org.wit.myfooddiary.helpers.read
import org.wit.myfooddiary.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "food.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<FoodModel>>() {}.type
val listTypeUser: Type = object : TypeToken<ArrayList<UserModel>>() {}.type



fun generateRandomId(): Long {
    return Random().nextLong()
}

class FoodItemJSONStore(private val context: Context) : FoodItemStore, UserStore {


    var foodItems = mutableListOf<FoodModel>()
    var users = mutableListOf<UserModel>()
    val list = concatenate(users, foodItems)

    init {
        if (exists(context, JSON_FILE)) {
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

    override fun deleteUser(user: UserModel) {
        users.remove(user)
        serialize()
    }


    override fun update(foodItem: FoodModel) {
        // todo
    }



    override fun findAllById(id: Long): List<FoodModel> {
        val iList = ArrayList<FoodModel>()
        for(f in foodItems)
            if(f.fUid == id){
                iList.add(f)
            }
        return iList

    }

    override fun findAllUsers(): List<UserModel> {
        TODO("Not yet implemented")
    }

    override fun findOneUser(id: Long):UserModel? {
        var foundUser: UserModel? = users.find { p -> p.Uid == id }
        return foundUser
    }

    override fun createUser(user: UserModel):UserModel {
        user.Uid = generateRandomId()
        users.add(user)
        serializeUser()
        return user
    }


    override fun updateUser(user: UserModel) {
        // todo
    }

    override fun checkCredientials(user: UserModel): UserModel? {
        var foundUser: UserModel? = users.find { p ->
            p.password == user.password &&
                    p.email == user.email
        }
        return foundUser

    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(foodItems, listType)

        val jsonString1 = gsonBuilder.toJson(users, listTypeUser)

        write(context, JSON_FILE, jsonString + jsonString1)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        foodItems = gsonBuilder.fromJson(jsonString, listType)
        val jsonString1 = read(context, JSON_FILE)

        users = gsonBuilder.fromJson(jsonString1, listTypeUser)

    }

    private fun serializeUser() {
        val jsonString1 = gsonBuilder.toJson(users, listTypeUser)

        write(context, JSON_FILE, jsonString1)

    }

    private fun deserializeUser() {
        val jsonString = read(context, JSON_FILE)

        users = gsonBuilder.fromJson(jsonString, listTypeUser)


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