package nl.johnny.movemate.api.models

data class User(override val id: Int, override val username: String,
                override val email: String,
                override val subscriptions: MutableList<Int>,
                override val subscribers: MutableList<Int>, ) : IUser