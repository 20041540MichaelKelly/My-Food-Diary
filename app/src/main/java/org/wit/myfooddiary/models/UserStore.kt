package org.wit.myfooddiary.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun checkCredientials(user: UserModel): Boolean

}