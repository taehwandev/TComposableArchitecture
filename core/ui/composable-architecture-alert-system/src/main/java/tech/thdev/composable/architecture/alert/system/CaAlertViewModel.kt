package tech.thdev.composable.architecture.alert.system

import androidx.compose.material3.SnackbarDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiStateDialogUiState
import tech.thdev.composable.architecture.base.CaViewModel
import javax.inject.Inject

@HiltViewModel
class CaAlertViewModel @Inject constructor(
    flowCaActionStream: FlowCaActionStream,
) : CaViewModel<CaAlertAction>(
    flowCaActionStream = flowCaActionStream,
    actionClass = CaAlertAction::class,
) {

    private val _alertUiStateDialogUiState = MutableStateFlow(CaAlertUiStateDialogUiState.Default)
    val alertUiStateDialogUiState = _alertUiStateDialogUiState.asStateFlow()

    private val _sideEffect = Channel<CaAlertSideEffect>(Channel.BUFFERED)
    internal val sideEffect = _sideEffect.receiveAsFlow()

    override suspend fun reducer(action: CaAlertAction) {
        when (action) {
            is CaAlertAction.ShowDialog -> {
                val dialogItem = CaAlertUiStateDialogUiState(
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
                _alertUiStateDialogUiState.value = dialogItem
                _sideEffect.send(CaAlertSideEffect.ShowDialog)
            }

            is CaAlertAction.HideDialog -> {
                _alertUiStateDialogUiState.value = CaAlertUiStateDialogUiState.Default
                _sideEffect.send(CaAlertSideEffect.HideDialog)
            }

            is CaAlertAction.ShowSnack -> {
                val snackItem = CaAlertSideEffect.ShowSnack(
                    message = action.message,
                    actionLabel = action.actionLabel,
                    onAction = action.onAction,
                    onDismiss = action.onDismiss,
                    duration = action.duration.convert(),
                )
                _sideEffect.send(snackItem)
            }

            is CaAlertAction.None -> {}
        }
    }

    private fun CaAlertAction.ShowSnack.Duration.convert(): SnackbarDuration =
        when (this) {
            CaAlertAction.ShowSnack.Duration.Short -> SnackbarDuration.Short
            CaAlertAction.ShowSnack.Duration.Indefinite -> SnackbarDuration.Indefinite
        }
}
