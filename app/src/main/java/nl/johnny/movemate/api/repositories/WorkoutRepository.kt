package nl.johnny.movemate.api.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nl.johnny.movemate.MoveMateApp
import nl.johnny.movemate.api.models.Workout
import nl.johnny.movemate.repositories.IWorkoutRepository
import org.json.JSONObject
import java.lang.Exception

class WorkoutRepository(app: MoveMateApp) : ApiRepository(app), IWorkoutRepository {

    companion object {
        const val TAG = "WORKOUT_REPO"
        val GSON = Gson()
    }


    override fun create(type: String, cb: (Workout?) -> Unit) {
        httpPost(
            path = "/workout",
            data = JSONObject(
                mapOf(
                    "type" to type
                )
            ),
            onSuccess = {
                try {
                    cb(GSON.fromJson(it.data, Workout::class.java))
                } catch (e: Exception) {
                    cb(null)
                }
            },
            onFailure = { cb(null) }
        )
    }

    override fun get(id: Int, cb: (Workout?) -> Unit) {
        httpGet(
            path = "/workout/$id",
            onSuccess = {
                try {
                    cb(GSON.fromJson(it.data, Workout::class.java))
                } catch (e: Exception) {
                    cb(null)
                }
            },
            onFailure = { cb(null) }
        )
    }

    override fun get(cb: (List<Workout>) -> Unit) {
        httpGet(
            path = "/workout",
            onSuccess = {
                try {
                    val typeToken = object : TypeToken<List<Workout>>() {}.type
                    cb(GSON.fromJson(it.data, typeToken))
                } catch (e: Exception) {
                    cb(listOf())
                }
            },
            onFailure = { cb(listOf()) }
        )
    }
}