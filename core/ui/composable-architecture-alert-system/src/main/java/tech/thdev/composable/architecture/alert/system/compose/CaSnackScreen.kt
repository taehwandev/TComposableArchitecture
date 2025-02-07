package tech.thdev.composable.architecture.alert.system.compose

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiState

@Composable
internal fun CaSnackScreen(
    snackbarHostState: SnackbarHostState,
    caAlertUiState: CaAlertUiState.Snack,
    onAction: (nextAction: CaAction) -> Unit,
) {
    LaunchedEffect(caAlertUiState) {
        val result = snackbarHostState
            .showSnackbar(
                message = caAlertUiState.message,
                actionLabel = caAlertUiState.actionLabel.takeIf { it.isNotEmpty() },
                duration = caAlertUiState.duration,
            )

        when (result) {
            SnackbarResult.ActionPerformed -> onAction(caAlertUiState.onAction)
            SnackbarResult.Dismissed -> onAction(caAlertUiState.onDismiss)
        }
    }
}
