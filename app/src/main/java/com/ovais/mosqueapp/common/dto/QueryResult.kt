package com.ovais.mosqueapp.common.dto


sealed interface QueryResult<out T> {
    data class Success<out T>(val data: T) : QueryResult<T>
    data class Failed(val message: String) : QueryResult<Nothing>
}