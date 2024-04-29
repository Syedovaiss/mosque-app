package com.ummah.mosque.common.composables

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


inline fun <reified Activity : ComponentActivity> Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        else -> {
            var context = this
            while (context is ContextWrapper) {
                context = context.baseContext
                if (context is Activity) return context
            }
            null
        }
    }
}

@ChecksSdkIntAtLeast(parameter = 0)
fun isVersionGreaterThanEquals(version: Int) = Build.VERSION.SDK_INT >= version

fun <T> ComponentActivity.collectFlowOnLifecycle(
    flow: Flow<T>,
    block: suspend (T) -> Unit,
    state: Lifecycle.State = Lifecycle.State.CREATED
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            flow.collectLatest(block)
        }
    }
}
fun ComponentActivity.getColorRes(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this.baseContext,resId)
}