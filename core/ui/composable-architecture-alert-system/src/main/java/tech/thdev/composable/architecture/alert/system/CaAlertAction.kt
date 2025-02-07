package tech.thdev.composable.architecture.alert.system

import android.view.View
import androidx.annotation.DrawableRes
import tech.thdev.composable.architecture.action.system.CaAction

sealed interface CaAlertAction : CaAction {

    data class Dialog(
        val title: String = "",
        val message: String = "",
        val confirmButtonText: String = "",
        val onConfirmButtonAction: CaAction = CaAction.None,
        val dismissButtonText: String = "",
        val onDismissButtonAction: CaAction = CaAction.None,
        val onDismissRequest: CaAction = CaAction.None,
        @DrawableRes val icon: Int = View.NO_ID,
        val dismissOnBackPress: Boolean = true,
        val dismissOnClickOutside: Boolean = true,
    ) : CaAlertAction

    data class Snack(
        val message: String = "",
        val actionLabel: String = "",
        val onAction: CaAction = CaAction.None,
        val onDismiss: CaAction = CaAction.None,
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
