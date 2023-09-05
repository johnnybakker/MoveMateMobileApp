package nl.johnny.movemate.api.models

import com.google.gson.JsonObject
import java.time.Instant
import java.util.Date

data class Workout(var id: Int, var type: String, var userId: Int, var startDate: Date, var endDate: Date?, var data: MutableList<JsonObject>)