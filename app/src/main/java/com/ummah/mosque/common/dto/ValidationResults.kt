package com.ummah.mosque.common.dto

sealed interface ValidationResults {
    data object Success : ValidationResults
    data class Failure(val message: String) : ValidationResults
}