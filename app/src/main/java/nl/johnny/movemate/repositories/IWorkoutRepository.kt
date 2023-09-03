package nl.johnny.movemate.repositories

import nl.johnny.movemate.api.models.Workout

interface IWorkoutRepository {
    fun create(type: String, cb: (Workout?) -> Unit)
    fun get(id: Int, cb: (Workout?) -> Unit)
    fun get(cb: (List<Workout>) -> Unit)
}