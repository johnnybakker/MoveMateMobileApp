package nl.johnny.movemate.repositories

import nl.johnny.movemate.api.models.CurrentUser
import nl.johnny.movemate.api.models.User

interface IUserRepository {
    fun subscribe(id: Int, callback: (Boolean) -> Unit)
    fun unsubscribe(id: Int, callback: (Boolean) -> Unit)

    fun getSubscribers(onSuccess: (subscribers: List<Int>) -> Unit, onFailure: () -> Unit)
    fun getSubscriptions(onSuccess: (subscriptions: List<Int>)->Unit, onFailure: () -> Unit)

    fun logout()

    fun get(onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit)
    fun login(email: String, password: String, onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit)
    fun signUp(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit)
    fun getAll(cb: (List<User>) -> Unit)
    fun search(value: String, cb: (List<User>) -> Unit)
}