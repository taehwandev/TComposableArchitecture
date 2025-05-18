package tech.thdev.composable.architecture.action.system.internal

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import tech.thdev.composable.architecture.action.system.Action
import tech.thdev.composable.architecture.action.system.ActionSender
import tech.thdev.composable.architecture.action.system.FlowActionStream
import javax.inject.Inject

@ViewModelScoped
internal class InternalActionImpl @Inject constructor() : FlowActionStream, ActionSender {

    private val flowCaAction = MutableSharedFlow<Action>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override fun flowAction(): Flow<Action> =
        flowCaAction.asSharedFlow()

    override fun send(action: Action) {
        flowCaAction.tryEmit(action)
    }

    override fun nextAction(action: Action) {
        send(action)
    }
}
