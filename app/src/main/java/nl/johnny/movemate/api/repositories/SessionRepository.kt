package nl.johnny.movemate.api.repositories

import nl.johnny.movemate.MoveMateApp
import nl.johnny.movemate.repositories.ISessionRepository
import org.json.JSONObject

class SessionRepository(app: MoveMateApp) : ApiRepository(app), ISessionRepository {
    override fun setFirebaseToken(token: String?) {
        if(app.userId == -1) return

        httpPost(
            "/session/${app.userId}/firebasetoken",
            data = JSONObject(mapOf("token" to token)),
            onSuccess = {

            },
            onFailure = {

            }
        )
    }
}