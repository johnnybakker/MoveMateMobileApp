package nl.johnny.movemate.api

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import java.lang.ClassCastException
import java.lang.IllegalArgumentException

class ApiResult(val type: ApiResultType, val data: JsonElement) {
    companion object {

        private val gson = Gson()

        fun parse(str: String?): ApiResult? {
            if(str == null) return null
            return try {

                val json = gson.fromJson(str, JsonObject::class.java)
                val type = json["type"].asString
                val data = json["data"]

                ApiResult(ApiResultType.valueOf(type), data)
            } catch (e: JsonSyntaxException) {
                null
            } catch (e: ClassCastException) {
                return null
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

}