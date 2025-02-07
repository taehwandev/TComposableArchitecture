package tech.thdev.composable.architecture.alert.system

import androidx.compose.material3.SnackbarDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.action.system.CaSideEffect
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiState
import tech.thdev.composable.architecture.base.CaViewModel
import javax.inject.Inject

@HiltViewModel
class CaAlertViewModel @Inject constructor(
    private val flowCaActionStream: FlowCaActionStream,
) : CaViewModel<CaAlertAction, CaSideEffect>(
    flowCaActionStream = flowCaActionStream,
    actionClass = CaAlertAction::class,
) {

    private val _alertUiState = MutableStateFlow<CaAlertUiState?>(null)
    internal val alertUiState = _alertUiState.asStateFlow()

    override suspend fun reducer(action: CaAlertAction): CaAction =
        when (action) {
            is CaAlertAction.Dialog -> {
                _alertUiState.value = CaAlertUiState.Dialog(
                    title = action.title,
                    message = action.message,
                    confirmButtonText = action.confirmButtonText,
                    onConfirmButtonAction = action.onConfirmButtonAction,
                    dismissButtonText = action.dismissButtonText,
                    onDismissButtonAction = action.onDismissButtonAction,
                    onDismissRequest = action.onDismissRequest,
                    icon = action.icon,
                    dismissOnBackPress = action.dismissOnBackPress,
                    dismissOnClickOutside = action.dismissOnClickOutside,
                )
                CaAction.None
            }

            is CaAlertAction.Snack -> {
                _alertUiState.value = CaAlertUiState.Snack(
                    message = action.message,
                    actionLabel = action.actionLabel,
                    onAction = action.onAction,
                    onDismiss = action.onDismiss,
                    duration = action.duration.convert(),
                )
                CaAction.None
            }
        }

    private fun CaAlertAction.Snack.Duration.convert(): SnackbarDuration =
        when (this) {
            CaAlertAction.Snack.Duration.Short -> SnackbarDuration.Short
            CaAlertAction.Snack.Duration.Indefinite -> SnackbarDuration.Indefinite
        }

    internal fun send(nextEvent: CaAction) {
        _alertUiState.value = null
        flowCaActionStream.nextAction(nextEvent)
    }
}
