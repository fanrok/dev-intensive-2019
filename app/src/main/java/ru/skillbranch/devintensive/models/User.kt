package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId", firstName = firstName, lastName = lastName, avatar = null)
        }
    }

    class Builder(
        var id: String = "1",
        var firstName: String? = null,
        var lastName: String? = null,
        var avatar: String? = null,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false) {
        fun id(id: String = "1") = apply { this.id = id }
        fun firstName(firstName: String?=null) = apply { this.firstName = firstName }
        fun lastName(lastName: String?=null) = apply { this.lastName = lastName }
        fun avatar(avatar: String?=null) = apply { this.avatar = avatar }
        fun rating(rating: Int = 0) = apply { this.rating = rating }
        fun respect(respect: Int = 0) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date? = Date()) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean = false) = apply { this.isOnline = isOnline }
        fun build() = User(id=id, firstName=firstName, lastName=lastName, avatar=avatar, rating=rating, respect=respect, lastVisit=lastVisit, isOnline=isOnline)
    }
}