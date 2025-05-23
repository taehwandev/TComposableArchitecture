package tech.thdev.composable.architecture.alert.system

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.thdev.composable.architecture.action.system.Action
import tech.thdev.composable.architecture.action.system.compose.LocalActionSenderOwner
import tech.thdev.composable.architecture.action.system.lifecycle.LaunchedLifecycleActionViewModel
import tech.thdev.composable.architecture.action.system.lifecycle.collectLifecycleEvent
import tech.thdev.composable.architecture.action.system.send
import tech.thdev.composable.architecture.alert.system.compose.CaDialogScreen
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiStateDialogUiState

@Composable
fun CaAlertScreen(
    snackbarHostState: SnackbarHostState,
    onDialogScreen: (@Composable (caAlertUiStateDialogUiState: CaAlertUiStateDialogUiState, onAction: (nextAction: Action) -> Unit) -> Unit)? = null,
) {
    InternalCaAlertScreen(
        snackbarHostState = snackbarHostState,
        onDialogScreen = onDialogScreen,
    )
}

@Composable
private fun InternalCaAlertScreen(
    snackbarHostState: SnackbarHostState,
    onDialogScreen: (@Composable (caAlertUiStateDialogUiState: CaAlertUiStateDialogUiState, onAction: (nextAction: Action) -> Unit) -> Unit)? = null,
    caAlertViewModel: ActionAlertViewModel = viewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }
    val action = LocalActionSenderOwner.current

    caAlertViewModel.sideEffect.collectLifecycleEvent { event ->
        when (event) {
            is CaAlertSideEffect.ShowDialog -> {
                showDialog = true
            }

            is CaAlertSideEffect.HideDialog -> {
                showDialog = false
            }

            is CaAlertSideEffect.ShowSnack -> {
                val result = snackbarHostState
                    .showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel.takeIf { it.isNotEmpty() },
                        duration = event.duration,
                    )

                when (result) {
                    SnackbarResult.ActionPerformed -> action.send(event.onAction).invoke()
                    SnackbarResult.Dismissed -> action.send(event.onDismiss).invoke()
                }
            }
        }
    }

    val caAlertUiStateDialogUiState by caAlertViewModel.alertUiStateDialogUiState.collectAsStateWithLifecycle()
    if (showDialog) {
        val onAction: (nextAction: Action) -> Unit = { nextAction ->
            action.send(CaAlertAction.HideDialog).invoke()
            action.send(nextAction).invoke()
        }
        onDialogScreen?.invoke(
            caAlertUiStateDialogUiState,
            onAction,
        ) ?: run {
            CaDialogScreen(
                caAlertUiStateDialogUiState = caAlertUiStateDialogUiState,
                onAction = onAction,
            )
        }
    }

    LaunchedLifecycleActionViewModel(
        viewModel = caAlertViewModel,
    )
}
