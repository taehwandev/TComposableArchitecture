package tech.thdev.composable.architecture.action.system

import kotlinx.coroutines.flow.Flow

/**
 * Definitions for utilizing [CaAction] events in a [tech.thdev.composable.architecture.base.CaViewModel].
 *
 * ```kotlin
 * class MainViewModel constructor(
 *     flowCaActionStream: FlowCaActionStream,
 * ) : ViewModel() {
 *
 *      private val _sideEffect = Channel<InternalSideEffect>(Channel.BUFFERED)
 *      internal val sideEffect = _sideEffect.receiveAsFlow()
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
 *     private fun reducer(action: CaAction) {
 *         return when (action) {
 *             is DefaultAction.AppEnd -> {
 *                  _sideEffect.send(SideEffect.End)
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
