package org.wit.myfooddiary.models

interface UserStore {
    fun findAllUsers(): List<UserModel>
    fun findOneUser(id: Long): UserModel?
    fun createUser(user: UserModel): UserModel
    fun updateUser(user: UserModel)
    fun checkCredientials(user: UserModel): UserModel?
    fun deleteUser(user: UserModel)
}