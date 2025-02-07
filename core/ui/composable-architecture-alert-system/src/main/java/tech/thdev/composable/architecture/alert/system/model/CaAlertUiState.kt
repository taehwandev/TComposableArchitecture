package tech.thdev.composable.architecture.alert.system.model

import android.view.View
import androidx.annotation.DrawableRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import tech.thdev.composable.architecture.action.system.CaAction

@Stable
internal sealed interface CaAlertUiState {

    @Immutable
    data class Dialog(
        val title: String,
        val message: String,
        val confirmButtonText: String,
        val onConfirmButtonAction: CaAction,
        val dismissButtonText: String,
        val onDismissButtonAction: CaAction,
        val onDismissRequest: CaAction,
        @DrawableRes val icon: Int,
        val dismissOnBackPress: Boolean,
        val dismissOnClickOutside: Boolean,
    ) : CaAlertUiState {

        companion object {

            val Default = Dialog(
                title = "",
                message = "",
                confirmButtonText = "",
                onConfirmButtonAction = CaAction.None,
                dismissButtonText = "",
                onDismissButtonAction = CaAction.None,
                onDismissRequest = CaAction.None,
                icon = View.NO_ID,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        }
    }

    @Immutable
    data class Snack(
        val message: String,
        val actionLabel: String,
        val onAction: CaAction,
        val onDismiss: CaAction,
        val duration: SnackbarDuration = if (actionLabel.isNotEmpty()) {
            SnackbarDuration.Indefinite
        } else {
            SnackbarDuration.Short
        },
    ) : CaAlertUiState {

        companion object {

            val Default = Snack(
                message = "",
                actionLabel = "",
                onAction = CaAction.None,
                onDismiss = CaAction.None,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }
}
