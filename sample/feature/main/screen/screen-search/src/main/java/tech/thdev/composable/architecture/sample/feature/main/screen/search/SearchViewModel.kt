package tech.thdev.composable.architecture.sample.feature.main.screen.search

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.router.system.Navigator
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter
import tech.thdev.composable.architecture.sample.feature.detail.api.model.DetailData
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    flowActionStream: FlowActionStream,
    private val navigator: Navigator,
) : ActionViewModel<MainAction>(flowActionStream, MainAction::class) {

    private val _sideEffect = Channel<SearchSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    override suspend fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.ShowToast -> {
                _sideEffect.send(SearchSideEffect.ShowToast)
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
                navigator.navigate(
                    activityRoute = DetailActivityRouter::class,
                    argumentMap = mapOf(
                        DetailActivityRouter.PUT_DATA to DetailData(text = action.message),
                    ),
                )
            }
        }
    }
}
