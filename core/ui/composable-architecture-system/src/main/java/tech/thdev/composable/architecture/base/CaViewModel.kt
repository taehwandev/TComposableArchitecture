package tech.thdev.composable.architecture.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.action.system.CaSideEffect
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import kotlin.reflect.KClass

/**
 * CaViewModel is a class designed to work with [tech.thdev.composable.architecture.action.system.CaActionSender].
 * It uses a similar structure to a reducer, allowing you to utilize it as shown below.  It also supports SideEffect handling.
 *
 * ```kotlin
 * @HiltViewModel
 * class MainViewModel @Inject constructor(
 *     flowCaActionStream: FlowCaActionStream,
 * ) : CaViewModel<Action, SideEffect>(flowCaActionStream, Action::class) {
 *
 *     override suspend fun reducer(action: Action): CaAction =
 *         when (action) {
 *             is Action.Send -> {
 *                 sendSideEffect(SideEffect.ShowToast)
 *                 CaActionNone
 *             }
 *         }
 * }
 * ```
 *
 * After inheriting from CaViewModel, you must handle two values in the base class:
 * - Required: Call `viewModel.loadAction()`
 * - Optional: Collect `viewModel.sideEffect` using `viewModel.sideEffect.collectAsEvent { ... }`
 *
 * [tech.thdev.composable.architecture.util.collectAsEvent] is a pre-defined function that collects side effects with a default lifecycle state of [Lifecycle.State.STARTED].
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
 *             // Your Composable view
 *
 *             LaunchedEffect(Unit) { // Required: Load actions
 *                 mainViewModel.loadAction()
 *             }
 *
 *             mainViewModel.sideEffect.collectAsEvent { // Optional: Handle side effects
 *                 when (it) {
 *                     SideEffect.ShowToast -> {
 *                         Toast.makeText(this@MainActivity, "message", Toast.LENGTH_SHORT).show()
 *                     }
 *                 }
 *             }
 *         }
 *     }
 * }
 * ```
 */
abstract class CaViewModel<ACTION : CaAction, SIDE_EFFECT : CaSideEffect>(
    flowCaActionStream: FlowCaActionStream,
    actionClass: KClass<ACTION>,
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SIDE_EFFECT>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val sideEffect = _sideEffect.asSharedFlow()

    @VisibleForTesting
    val flowAction by lazy(LazyThreadSafetyMode.NONE) {
        flowCaActionStream.flowAction()
            .filterIsInstance(actionClass)
            .map {
                reducer(action = it)
            }
            .onEach {
                flowCaActionStream.nextAction(it)
            }
    }

    fun loadAction() {
        flowAction
            .launchIn(viewModelScope)
    }

    fun sendSideEffect(sideEffect: SIDE_EFFECT) {
        _sideEffect.tryEmit(sideEffect)
    }

    abstract suspend fun reducer(action: ACTION): CaAction
}
