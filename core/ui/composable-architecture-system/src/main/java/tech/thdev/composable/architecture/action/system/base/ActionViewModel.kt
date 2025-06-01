package tech.thdev.composable.architecture.action.system.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.thdev.composable.architecture.action.system.Action
import tech.thdev.composable.architecture.action.system.ActionSender
import tech.thdev.composable.architecture.action.system.FlowActionStream
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * CaViewModel is a class designed to work with [tech.thdev.composable.architecture.action.system.ActionSender].
 * It uses a similar structure to a reducer, allowing you to utilize it as shown below.  It also supports SideEffect handling.
 *
 * ```kotlin
 * class MainViewModel constructor(
 *     flowActionStream: FlowActionStream,
 * ) : ActionViewModel<SomeAction>(flowActionStream, SomeAction::class) {
 *
 *      private val _sideEffect = Channel<SideEffect>(Channel.BUFFERED)
 *      internal val sideEffect = _sideEffect.receiveAsFlow()
 *
 *     override suspend fun handleAction(action: SomeAction) =
 *         when (action) {
 *             is SomeAction.Event -> {
 *                 _sideEffect.send(SideEffect.ShowToast)
 *                 // or
 *                 flowCaActionStream.nextAction(SomeAction.ShowToast)
 *             }
 *
 *             is SomeAction.ShowToast -> {
 *                 // your code
 *             }
 *         }
 * }
 * ```
 *
 * After inheriting from CaViewModel, you must handle two values in the base class:
 * - Required: Call `viewModel.loadAction()`
 * - Optional: Collect `viewModel.sideEffect` using `viewModel.sideEffect.collectAsEvent { ... }`
 *
 * ```kotlin
 * @Composable
 * fun SomeScreen(
 *      someViewModel: SomeViewModel = viewModel(), // or, Use navigation hiltViewModel()
 * ) {
 *     ActionSenderCompositionLocalProvider(someViewModel) {
 *          val uiState by someViewModel.uiState.collectAsStateWithLifecycle()
 *          SomeScreen(
 *              uiState = uiState,
 *          )
 *     }
 * }
 * ```
 */
abstract class ActionViewModel<ACTION : Action>(
    private val flowActionStream: FlowActionStream,
    actionClass: KClass<ACTION>,
) : ViewModel() {

    @Inject
    internal lateinit var actionSender: ActionSender

    @VisibleForTesting
    var flowActionJob: Job? = null

    @VisibleForTesting
    var isFirst = true

    @VisibleForTesting
    val flowAction by lazy {
        flowActionStream.flowAction()
            .filterIsInstance(actionClass)
            .onEach {
                handleAction(action = it)
            }
    }

    internal fun loadAction() {
        cancelAction()

        flowActionJob = flowAction
            .launchIn(viewModelScope)

        if (isFirst) {
            onCreated()
            isFirst = false
        }
    }

    /**
     * ViewModel created only call once.
     */
    open fun onCreated() {}

    internal fun cancelAction() {
        if (flowActionJob?.isActive == true) {
            flowActionJob?.cancel()
            flowActionJob = null
        }
    }

    fun nextAction(action: ACTION) {
        flowActionStream.nextAction(action)
    }

    abstract suspend fun handleAction(action: ACTION)
}
