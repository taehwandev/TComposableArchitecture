package tech.thdev.composable.architecture.router.system.internal

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.router.system.CaJourneyMapper
import tech.thdev.composable.architecture.router.system.CaRouterAction
import javax.inject.Inject

@HiltViewModel
class InternalActionRouterViewModel @Inject internal constructor(
    flowActionStream: FlowActionStream,
    private val journeyMapper: CaJourneyMapper,
) : ActionViewModel<CaRouterAction>(
    flowActionStream = flowActionStream,
    actionClass = CaRouterAction::class,
) {

    private val _sideEffect = Channel<InternalCaSideEffect>(Channel.BUFFERED)
    internal val sideEffect = _sideEffect.receiveAsFlow()

    override suspend fun handleAction(action: CaRouterAction) {
        when (action) {
            is CaRouterAction.MoveNavigation -> {
                _sideEffect.send(InternalCaSideEffect.MoveNavigation(navigationRoute = action.navigationRoute))
            }

            is CaRouterAction.MoveNavigationBack -> {
                _sideEffect.send(InternalCaSideEffect.MoveNavigationBack)
            }

            is CaRouterAction.MoveActivityVisit<*> -> {
                journeyMapper.getJourneyOrNull(action.activityRoute.java)?.let {
                    _sideEffect.send(
                        InternalCaSideEffect.MoveActivityVisit(
                            activityRoute = it,
                            argumentMap = action.argumentMap,
                        )
                    )
                }
            }

            is CaRouterAction.MoveActivityBack -> {
                _sideEffect.send(InternalCaSideEffect.MoveActivityBack)
            }
        }
    }
}
