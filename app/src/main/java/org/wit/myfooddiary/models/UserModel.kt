package org.wit.myfooddiary.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                     var firstName: String = "",
                     var lastName: String = "",
                     var email: String = "",
                     var password: String = "",
                     var image: Uri = Uri.EMPTY) : Parcelable
