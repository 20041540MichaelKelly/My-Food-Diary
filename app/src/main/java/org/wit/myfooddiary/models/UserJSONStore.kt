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

const val JSON_FILE = "users.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type



fun generateRandomUId(): Long {
    return Random().nextLong()
}

class UserJSONStore(private val context: Context) : UserStore {


    var foodItems = mutableListOf<FoodModel>()
    var users = mutableListOf<UserModel>()


    init {
        if (exists(context, JSON_FILE)) {
            deserialize()

        }
    }


    override fun deleteUser(user: UserModel) {
        users.remove(user)
        serialize()
    }

    override fun findAllUsers(): List<UserModel> {
        TODO("Not yet implemented")
    }

    override fun findOneUser(id: Long):UserModel? {
        var foundUser: UserModel? = users.find { p -> p.Uid == id }
        return foundUser
    }

    override fun createUser(user: UserModel):UserModel {
        user.Uid = generateRandomUId()
        users.add(user)
        serialize()
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
        val jsonString = gsonBuilder.toJson(users, listType)


        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = gsonBuilder.fromJson(jsonString, listType)


    }


    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }
}

//class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): Uri {
//        return Uri.parse(json?.asString)
//    }
//
//    override fun serialize(
//        src: Uri?,
//        typeOfSrc: Type?,
//        context: JsonSerializationContext?
//    ): JsonElement {
//        return JsonPrimitive(src.toString())
//    }
//
//
//}