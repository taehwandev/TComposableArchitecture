package tech.thdev.composable.architecture.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import kotlin.reflect.KClass

/**
 * CaViewModel is a class designed to work with [tech.thdev.composable.architecture.action.system.CaActionSender].
 * It uses a similar structure to a reducer, allowing you to utilize it as shown below.  It also supports SideEffect handling.
 *
 * ```kotlin
 * class MainViewModel constructor(
 *     flowCaActionStream: FlowCaActionStream,
 * ) : CaViewModel<Action>(flowCaActionStream, Action::class) {
 *
 *      private val _sideEffect = Channel<SideEffect>(Channel.BUFFERED)
 *      internal val sideEffect = _sideEffect.receiveAsFlow()
 *
 *     override suspend fun reducer(action: YourAction) =
 *         when (action) {
 *             is YourAction.ShowToast -> {
 *                 _sideEffect.send(SideEffect.ShowToast)
 *                 // or
 *                 flowCaActionStream.nextAction(NextAction.ShowToast)
 *             }
 *         }
 * }
 * ```
 *
 * After inheriting from CaViewModel, you must handle two values in the base class:
 * - Required: Call `viewModel.loadAction()`
 * - Optional: Collect `viewModel.sideEffect` using `viewModel.sideEffect.collectAsEvent { ... }`
 *
 * [tech.thdev.composable.architecture.lifecycle.collectLifecycleEvent] is a pre-defined function that collects side effects with a default lifecycle state of [Lifecycle.State.STARTED].
 *
 * ```kotlin
 * @AndroidEntryPoint
 * class MainActivity : CaActionActivity() {
 *
 *     private val mainViewModel by viewModels<MainViewModel>()
 *
 *     @Composable
 *     override fun ContentView() {
 *         TComposableArchitectureTheme {
 *             LaunchedLifecycleViewModel(viewModel = mainViewModel) // Required
 *
 *             // Your Compose view
 *         }
 *     }
 * }
 * ```
 */
abstract class CaViewModel<CA_ACTION : CaAction>(
    private val flowCaActionStream: FlowCaActionStream,
    actionClass: KClass<CA_ACTION>,
) : ViewModel() {

    @VisibleForTesting
    var flowActionJob: Job? = null

    @VisibleForTesting
    val flowAction by lazy(LazyThreadSafetyMode.NONE) {
        flowCaActionStream.flowAction()
            .filterIsInstance(actionClass)
            .onEach {
                reducer(action = it)
            }
    }

    internal fun loadAction() {
        cancelAction()

        flowActionJob = flowAction
            .launchIn(viewModelScope)

        onLoad()
    }

    open fun onLoad() { // custom open
        // Do nothing
    }

    internal fun cancelAction() {
        flowActionJob?.cancel()
        flowActionJob = null

        onCancel()
    }

    open fun onCancel() { // custom open
        // Do nothing
    }

    fun nextAction(action: CaAction) {
        flowCaActionStream.nextAction(action)
    }

    abstract suspend fun reducer(action: CA_ACTION)
}
