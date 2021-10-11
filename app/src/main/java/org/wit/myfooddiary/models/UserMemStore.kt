package org.wit.myfooddiary.models

import timber.log.Timber.i

class UserMemStore : UserStore {
    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = getId()
        users.add(user)
        logAll()
    }

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.firstName = user.firstName
            foundUser.email = user.email
            logAll()
        }
    }

    override fun checkCredientials(user: UserModel): Boolean {
        var foundUser: UserModel? = users.find { p ->
            p.password == user.password
        }
        if(foundUser != null) {
            return true
        }

        return false
    }

    private fun logAll() {
        users.forEach{ i("${it}") }
    }
}