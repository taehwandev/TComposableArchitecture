package tech.thdev.composable.architecture.lifecycle

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectLifecycleEvent(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    onBody: (item: T) -> Unit,
) {
    val body by remember { mutableStateOf(onBody) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(body) {
        lifecycle.repeatOnLifecycle(state) {
            collectLatest {
                body(it)
            }
        }
    }
}
