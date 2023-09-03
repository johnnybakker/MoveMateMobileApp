package nl.johnny.movemate.api.models

data class CurrentUser(
    override val id: Int,
    override val username: String,
    override val email: String,
    val token: String,
    override val subscriptions: MutableList<Int>,
    override val subscribers: MutableList<Int>
) : IUser