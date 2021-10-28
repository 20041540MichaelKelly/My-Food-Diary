package org.wit.myfooddiary.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodModel(var id: Long = 0L,
                     var title: String = "",
                     var description: String = "",
                     var image: Uri = Uri.EMPTY,
                     var lat : Double = 0.0,
                     var lng: Double = 0.0,
                     var zoom: Float = 0f,
                     var Uid: Long = 0L,
                     var firstName: String = "",
                     var lastName: String = "",
                     var email: String = "",
                     var password: String = "",
                     var Uimage: Uri = Uri.EMPTY) : Parcelable


