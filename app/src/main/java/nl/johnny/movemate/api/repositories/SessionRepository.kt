package nl.johnny.movemate.api.repositories

import android.content.Context
import nl.johnny.movemate.api.ApiService
import nl.johnny.movemate.repositories.ISessionRepository
import org.json.JSONObject

class SessionRepository(context: Context, service: ApiService) : ApiRepository(context, service), ISessionRepository {
    override fun setFirebaseToken(token: String?) {
        if(service.userId == -1) return
        if(service.firebaseToken == token) return

        httpPost(
            "/session/${service.userId}/firebasetoken",
            data = JSONObject(mapOf("token" to token)),
            onSuccess = {
                service.firebaseToken = token
            },
            onFailure = {
                service.firebaseToken = null
            }
        )
    }
}