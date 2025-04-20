package tech.thdev.composable.architecture.alert.system.model

import android.view.View
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.alert.system.CaAlertAction

@Immutable
data class CaAlertUiStateDialogUiState(
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
) {

    companion object {

        val Default = CaAlertUiStateDialogUiState(
            title = "",
            message = "",
            confirmButtonText = "",
            onConfirmButtonAction = CaAlertAction.None,
            dismissButtonText = "",
            onDismissButtonAction = CaAlertAction.None,
            onDismissRequest = CaAlertAction.None,
            icon = View.NO_ID,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    }
}
