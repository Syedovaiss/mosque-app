package com.ummah.mosque.common.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import com.google.android.gms.tasks.Task
import com.ummah.mosque.common.dto.QueryResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

const val EMPTY_STRING = ""
fun String?.default(value: String? = null) = this ?: value ?: ""
fun Int?.default(value: Int? = null) = this ?: value ?: 0
fun Long?.default(value: Long? = null) = this ?: value ?: 0L
fun Boolean?.default(value: Boolean? = null) = this ?: value ?: false


suspend fun <T> Task<T>.completeAsQueryResult(): QueryResult<T> {
    return suspendCancellableCoroutine { continuation ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(QueryResult.Success(task.result))
            } else {
                continuation.resume(QueryResult.Failed(task.exception?.message ?: ""))
            }
        }
    }
}
fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}