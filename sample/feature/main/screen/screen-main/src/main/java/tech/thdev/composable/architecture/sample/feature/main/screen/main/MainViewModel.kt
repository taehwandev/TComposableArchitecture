package tech.thdev.composable.architecture.sample.feature.main.screen.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    flowActionStream: FlowActionStream,
) : ActionViewModel<MainAction>(flowActionStream, MainAction::class) {

    private val _sideEffect = Channel<MainSideEffect>(Channel.BUFFERED)
    internal val sideEffect = _sideEffect.receiveAsFlow()

    override suspend fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.ShowToast -> {
                _sideEffect.send(MainSideEffect.ShowToast)
            }

            is MainAction.ShowAlert -> {
//                nextAction( // todo
//                    CaAlertAction.ShowDialog(
//                        icon = action.icon,
//                        title = action.title,
//                        message = action.message,
//                        confirmButtonText = action.confirmButtonText,
//                        onConfirmButtonAction = CaAlertAction.ShowSnack(
//                            message = "Confirm",
//                        ),
//                        dismissButtonText = action.dismissButtonText,
//                        onDismissButtonAction = CaAlertAction.ShowSnack(
//                            message = "Dismiss",
//                        ),
//                    )
//                )
            }

            is MainAction.ShowDetail -> {
//                nextAction( // todo
//                    CaRouterAction.MoveActivityVisit(
//                        activityRoute = DetailActivityRouter::class,
//                        argumentMap = mapOf(
//                            DetailActivityRouter.PUT_DATA to DetailData(text = action.message),
//                        ),
//                    )
//                )
            }
        }
    }
}
