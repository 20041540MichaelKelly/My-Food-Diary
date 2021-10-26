package org.wit.myfooddiary.models

import timber.log.Timber.i

var lastUId = 0L

internal fun getUId(): Long {
    return lastUId++
}

class UserMemStore : UserStore {
    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = getUId()
        users.add(user)
        logAll()
    }

    override fun findOne(id: Long) : UserModel? {
        var foundUser: UserModel? = users.find { p -> p.id == id }
        return foundUser
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