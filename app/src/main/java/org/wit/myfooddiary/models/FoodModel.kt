package org.wit.myfooddiary.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Time


@Parcelize
    data class FoodModel(
        var fUid: Long = 0L,
        var id: Long = 0L,
        var title: String = "",
        var description: String = "",
        var timeForFood: String = "",
        var amountOfCals: Int = 0,
        var image: Uri = Uri.EMPTY,
        var lat: Double = 0.0,
        var lng: Double = 0.0,
        var zoom: Float = 0f
    ) : Parcelable


