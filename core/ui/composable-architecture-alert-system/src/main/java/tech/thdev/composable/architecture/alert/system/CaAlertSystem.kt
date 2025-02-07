package tech.thdev.composable.architecture.alert.system

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.thdev.composable.architecture.alert.system.compose.CaDialogScreen
import tech.thdev.composable.architecture.alert.system.compose.CaSnackScreen
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiState

@Composable
fun CaAlertScreen(
    snackbarHostState: SnackbarHostState,
) {
    InternalCaAlertScreen(
        snackbarHostState = snackbarHostState,
    )
}

@Composable
private fun InternalCaAlertScreen(
    snackbarHostState: SnackbarHostState,
    caAlertViewModel: CaAlertViewModel = viewModel(),
) {
    val alertUiState by caAlertViewModel.alertUiState.collectAsStateWithLifecycle()

    when (alertUiState) {
        is CaAlertUiState.Dialog -> {
            CaDialogScreen(
                caAlertUiState = alertUiState as CaAlertUiState.Dialog,
                onAction = caAlertViewModel::send,
            )
        }

        is CaAlertUiState.Snack -> {
            CaSnackScreen(
                snackbarHostState = snackbarHostState,
                caAlertUiState = alertUiState as CaAlertUiState.Snack,
                onAction = caAlertViewModel::send,
            )
        }

        else -> {} // Do nothing
    }

    LaunchedEffect(Unit) {
        caAlertViewModel.loadAction()
    }
}
