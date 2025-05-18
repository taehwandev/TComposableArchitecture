package tech.thdev.composable.architecture.action.system.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import tech.thdev.composable.architecture.action.system.base.ActionViewModel

/**
 * This code must be used in conjunction with CaViewModel<*>.
 * It can be used as shown below.
 *
 * ```kotlin
 * class MainActivity : CaActionActivity() {
 *
 *      private val mainViewModel by viewModels<MainViewModel>()
 *
 *      @Composable
 *      override fun ContentView() {
 *      TComposableArchitectureTheme {
 *          LaunchedLifecycleViewModel(viewModel = mainViewModel) // Required
 *
 *          // Your Compose view
 *      }
 *  }
 * }
 * ```
 */
@Composable
fun LaunchedLifecycleActionViewModel(viewModel: ActionViewModel<*>) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.loadAction()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.cancelAction()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            viewModel.cancelAction()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
