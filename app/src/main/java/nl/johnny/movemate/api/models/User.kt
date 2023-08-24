package nl.johnny.movemate.api.models

data class User(override val id: Int, override val username: String) : IUser