package org.wit.myfooddiary.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
    data class FoodModel(
    var fid: String? = "",
    var uid: String? = "",
    var title: String = "",
    var description: String = "",
    var timeForFood: String = "",
    var amountOfCals: Int = 0,
    var image: String = "",
 //   var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var email: String? = "joe@bloggs.com")
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "fid" to fid,
            "uid" to uid,
            "title" to title,
            "description" to description,
            "timeForFood" to timeForFood,
            "amountOfCals" to amountOfCals,
            "image" to image,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom
        )
    }
}



