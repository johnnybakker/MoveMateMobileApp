package nl.johnny.movemate.api.models

import com.google.gson.JsonObject
import java.time.Instant
import java.util.Date

data class Workout(var id: Int, var type: String, var userid: Int, var startdate: Date, var enddate: Date?, var data: MutableList<JsonObject>)