package tech.thdev.composable.architecture.action.system.internal

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.action.system.CaActionSender
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalCaAction @Inject constructor() : FlowCaActionStream, CaActionSender {

    private val flowCaAction = MutableSharedFlow<CaAction>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override fun flowAction(): Flow<CaAction> =
        flowCaAction.asSharedFlow()

    override fun send(action: CaAction) {
        flowCaAction.tryEmit(action)
    }

    override fun nextAction(action: CaAction) {
        send(action)
    }
}
