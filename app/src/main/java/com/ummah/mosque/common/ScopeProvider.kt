package com.ummah.mosque.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Inject

interface ScopeProvider {
    val background: CoroutineScope
    val default: CoroutineScope
    val main: CoroutineScope
}

class DefaultScopeProvider @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : ScopeProvider {
    override val main: CoroutineScope
        get() = CoroutineScope(Job() + dispatcherProvider.ui)
    override val background: CoroutineScope
        get() = CoroutineScope(Job() + dispatcherProvider.background)
    override val default: CoroutineScope
        get() = CoroutineScope(Job() + dispatcherProvider.default)
}