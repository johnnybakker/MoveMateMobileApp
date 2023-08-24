package nl.johnny.movemate.repositories

import nl.johnny.movemate.api.models.CurrentUser

interface ISessionRepository {
    fun setFirebaseToken(token: String?)
}