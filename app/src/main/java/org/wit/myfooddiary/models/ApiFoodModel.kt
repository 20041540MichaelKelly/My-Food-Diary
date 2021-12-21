package org.wit.myfooddiary.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
    data class ApiFoodModel(
    var fid: String? = "",
    var uid: String? = "",
    @SerializedName("title")
    var title: String = "",
    var timeForFood: String = "",
    @SerializedName("calories")
    var amountOfCals: Int = 0,
    @SerializedName("image")
    var image: String = ""
    ) : Parcelable



