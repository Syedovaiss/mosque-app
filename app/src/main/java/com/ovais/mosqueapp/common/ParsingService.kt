package com.ovais.mosqueapp.common

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ParsingService @Inject constructor(
    val json: Json
) {
    fun encodeToString(data: Any): String {
        return json.encodeToString(data)
    }

    inline fun <reified T> decodeFromString(data: String): T? {
        return json.decodeFromString<T?>(data)
    }
}
inline fun <reified T>encodeToString(data: T): String {
    return Json.encodeToString(data)
}