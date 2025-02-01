package tech.thdev.composable.architecture.action.system

import kotlinx.coroutines.flow.Flow

/**
 * Definitions for utilizing [CaAction] events in a [ViewModel].
 *
 * ```kotlin
 * @HiltViewModel
 * class MainViewModel @Inject constructor(
 *     flowCaActionStream: FlowCaActionStream,
 * ) : ViewModel() {
 *
 *     // Handle events received through CaActionSender
 *     @VisibleForTesting
 *     val flowAction by lazy(LazyThreadSafetyMode.NONE) {
 *         flowCaActionStream.flowAction()
 *             .filterIsInstance<Action.AppEnd>()
 *             .flatMapLatest {
 *                 reducer(it)
 *             }
 *             .onEach {
 *                 // If the reducer generates an event, pass it on
 *                 flowActionStream.nextAction(it)
 *             }
 *     }
 *
 *     // Definition for using CA Action as a reducer
 *     private fun reducer(action: CaAction): Flow<CaAction> {
 *         return when (action) {
 *             is DefaultAction.AppEnd -> {
 *                 flowOf(CaActionNone)
 *                     .onEach {
 *                         _sideEffect.tryEmit(SideEffect.End)
 *                     }
 *             }
 *
 *             else -> {
 *                 flowOf(CaActionNone)
 *             }
 *         }
 *     }
 * }
 * ```
 */
interface FlowCaActionStream {

    fun flowAction(): Flow<CaAction>

    fun nextAction(action: CaAction)
}
