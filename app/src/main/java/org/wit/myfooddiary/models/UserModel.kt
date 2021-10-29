package org.wit.myfooddiary.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var Uid: Long = 0,
                     var firstName: String = "",
                     var lastName: String = "",
                     var email: String = "",
                     var password: String = "",
                     var foodObject: ArrayList<FoodModel> = ArrayList(),
                     var Uimage: Uri = Uri.EMPTY) : Parcelable


