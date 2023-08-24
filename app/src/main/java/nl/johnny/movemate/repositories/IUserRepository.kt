package nl.johnny.movemate.repositories

import nl.johnny.movemate.api.models.CurrentUser
import nl.johnny.movemate.api.models.User

interface IUserRepository {
    fun get(onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit)
    fun logout()
    fun login(email: String, password: String, onSuccess: (CurrentUser) -> Unit, onFailure: () -> Unit)
    fun signUp(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit)
    fun getAll(cb: (List<User>) -> Unit)
    fun search(value: String, cb: (List<User>) -> Unit)
}