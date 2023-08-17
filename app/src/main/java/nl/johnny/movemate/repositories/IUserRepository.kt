package nl.johnny.movemate.repositories

interface IUserRepository {
    fun isLoggedIn() : Boolean

    fun logOut()
    fun logIn(email: String, password: String, onSuccess: () -> Unit)
    fun signUp(username: String, email: String, password: String, onSuccess: () -> Unit)
    fun getAll(cb: (String) -> Unit)
}