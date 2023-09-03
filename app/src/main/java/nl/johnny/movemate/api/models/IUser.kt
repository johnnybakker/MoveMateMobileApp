package nl.johnny.movemate.api.models

interface IUser {
    val id: Int
    val username: String
    val email: String
    val subscriptions: MutableList<Int>
    val subscribers: MutableList<Int>
}