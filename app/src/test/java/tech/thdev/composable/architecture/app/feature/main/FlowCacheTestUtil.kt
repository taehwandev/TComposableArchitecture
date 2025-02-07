package tech.thdev.composable.architecture.app.feature.main

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher

internal fun <T> Flow<T>.awaitTest(onCallback: (item: T) -> Unit): Job =
    CoroutineScope(UnconfinedTestDispatcher()).launch {
        first().also {
            onCallback(it)
            cancel()
        }
    }
