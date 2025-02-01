package tech.thdev.composable.architecture.app.feature.main

import dagger.hilt.android.lifecycle.HiltViewModel
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.action.system.CaActionNone
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.base.CaViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    flowCaActionStream: FlowCaActionStream,
) : CaViewModel<Action, SideEffect>(flowCaActionStream, Action::class) {

    override suspend fun reducer(action: Action): CaAction =
        when (action) {
            is Action.Send -> {
                sendSideEffect(SideEffect.ShowToast)
                CaActionNone
            }
        }
}
