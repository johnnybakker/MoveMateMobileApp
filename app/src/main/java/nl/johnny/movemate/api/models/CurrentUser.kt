package nl.johnny.movemate.api.models

data class CurrentUser(
    override val id: Int,
    override val username: String,
    val email: String,
    val token: String
) : IUser