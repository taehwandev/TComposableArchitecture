package tech.thdev.composable.architecture.alert.system

import android.view.View
import androidx.annotation.DrawableRes
import tech.thdev.composable.architecture.action.system.CaAction

sealed interface CaAlertAction : CaAction {

    data object None : CaAlertAction

    data class Dialog(
        val title: String = "",
        val message: String = "",
        val confirmButtonText: String = "",
        val onConfirmButtonAction: CaAction = None,
        val dismissButtonText: String = "",
        val onDismissButtonAction: CaAction = None,
        val onDismissRequest: CaAction = None,
        @DrawableRes val icon: Int = View.NO_ID,
        val dismissOnBackPress: Boolean = true,
        val dismissOnClickOutside: Boolean = true,
    ) : CaAlertAction

    data class Snack(
        val message: String = "",
        val actionLabel: String = "",
        val onAction: CaAction = None,
        val onDismiss: CaAction = None,
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
