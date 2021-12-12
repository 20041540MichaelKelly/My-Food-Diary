package org.wit.myfooddiary.models

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
    data class FoodModel(
    var fUid: Long = 0L,
    var id: Long = 0L,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var description: String = "",
    var timeForFood: String = "",
    @SerializedName("calories")
    var amountOfCals: Int = 0,
    @SerializedName("image")
    var image: String = "",
//    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
    ) : Parcelable


