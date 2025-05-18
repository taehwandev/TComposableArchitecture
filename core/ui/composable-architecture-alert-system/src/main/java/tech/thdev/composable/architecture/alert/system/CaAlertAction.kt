package tech.thdev.composable.architecture.alert.system

import android.view.View
import androidx.annotation.DrawableRes
import tech.thdev.composable.architecture.action.system.Action

sealed interface CaAlertAction : Action {

    data object None : CaAlertAction

    data object HideDialog : CaAlertAction

    data class ShowDialog(
        val title: String = "",
        val message: String = "",
        val confirmButtonText: String = "",
        val onConfirmButtonAction: Action = None,
        val dismissButtonText: String = "",
        val onDismissButtonAction: Action = None,
        val onDismissRequest: Action = None,
        @DrawableRes val icon: Int = View.NO_ID,
        val dismissOnBackPress: Boolean = true,
        val dismissOnClickOutside: Boolean = true,
    ) : CaAlertAction

    data class ShowSnack(
        val message: String = "",
        val actionLabel: String = "",
        val onAction: Action = None,
        val onDismiss: Action = None,
        val duration: Duration = if (actionLabel.isNotEmpty()) {
            Duration.Indefinite
        } else {
            Duration.Short
        },
    ) : CaAlertAction {

        enum class Duration {
            Short,
            Indefinite,
        }
    }
}
