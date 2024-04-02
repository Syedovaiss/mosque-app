package com.ovais.mosqueapp.features.register.data

import com.ovais.mosqueapp.common.utils.default
import kotlinx.serialization.Serializable
import java.util.UUID
import kotlin.collections.HashMap

@Serializable
data class UserInfo(
    val userId: String = UUID.randomUUID().toString(),
    val username: String,
    var password: String,
    val country: String,
    val countryId: Long,
    val city: String,
    val cityId: Long,
    val phoneNumber: String
) {
    companion object {
        private const val USER_ID = "userId"
        private const val USERNAME = "username"
        private const val CITY = "city"
        private const val CITY_ID = "cityId"
        private const val COUNTRY = "country"
        private const val COUNTRY_ID = "countryId"
        private const val PASSWORD = "password"
        private const val PHONE_NUMBER = "phoneNumber"
        fun UserInfo.asHashMap(): HashMap<Any, Any> {
            return hashMapOf(
                USER_ID to userId,
                USERNAME to username,
                CITY to city,
                CITY_ID to cityId,
                COUNTRY to country,
                COUNTRY_ID to countryId,
                PASSWORD to password,
                PHONE_NUMBER to phoneNumber
            )
        }

        fun fromHashMap(data: HashMap<*, *>): UserInfo {
            return UserInfo(
                userId = data[USER_ID].toString(),
                username = data[USERNAME].toString(),
                password = data[PASSWORD].toString(),
                country = data[COUNTRY].toString(),
                countryId = data[COUNTRY_ID].toString().toLongOrNull().default(),
                city = data[CITY].toString(),
                cityId = data[CITY_ID].toString().toLongOrNull().default(),
                phoneNumber = data[PHONE_NUMBER].toString()
            )
        }
    }
}
