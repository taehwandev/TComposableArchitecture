package tech.thdev.composable.architecture.alert.system

import androidx.compose.material3.SnackbarDuration
import tech.thdev.composable.architecture.action.system.CaAction

internal sealed interface CaAlertSideEffect {

    data object ShowDialog : CaAlertSideEffect

    data object HideDialog : CaAlertSideEffect

    data class ShowSnack(
        val message: String,
        val actionLabel: String,
        val onAction: CaAction,
        val onDismiss: CaAction,
        val duration: SnackbarDuration = if (actionLabel.isNotEmpty()) {
            SnackbarDuration.Indefinite
        } else {
            SnackbarDuration.Short
        },
    ) : CaAlertSideEffect {

        companion object {

            val Default = ShowSnack(
                message = "",
                actionLabel = "",
                onAction = CaAlertAction.None,
                onDismiss = CaAlertAction.None,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }
}
