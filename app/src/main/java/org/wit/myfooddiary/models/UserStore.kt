package org.wit.myfooddiary.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(foodItem: UserModel)
    fun update(foodItem: UserModel)

}