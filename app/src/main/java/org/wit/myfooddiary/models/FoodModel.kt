package org.wit.myfooddiary.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodModel(var title: String = "",
                     var description: String = "") : Parcelable
