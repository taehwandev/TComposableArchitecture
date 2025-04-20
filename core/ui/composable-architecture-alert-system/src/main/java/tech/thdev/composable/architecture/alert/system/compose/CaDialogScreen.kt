package tech.thdev.composable.architecture.alert.system.compose

import android.view.View
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiStateDialogUiState

@Composable
internal fun CaDialogScreen(
    caAlertUiStateDialogUiState: CaAlertUiStateDialogUiState,
    onAction: (nextAction: CaAction) -> Unit,
) {
    AlertDialog(
        icon = {
            if (caAlertUiStateDialogUiState.icon != View.NO_ID) {
                Icon(
                    painter = painterResource(id = caAlertUiStateDialogUiState.icon),
                    contentDescription = null,
                )
            }
        },
        title = {
            if (caAlertUiStateDialogUiState.title.isNotEmpty()) {
                Text(text = caAlertUiStateDialogUiState.title)
            }
        },
        text = {
            if (caAlertUiStateDialogUiState.message.isNotEmpty()) {
                Text(text = caAlertUiStateDialogUiState.message)
            }
        },
        onDismissRequest = {
            onAction(caAlertUiStateDialogUiState.onDismissRequest)
        },
        confirmButton = {
            if (caAlertUiStateDialogUiState.confirmButtonText.isNotEmpty()) {
                TextButton(
                    onClick = {
                        onAction(caAlertUiStateDialogUiState.onConfirmButtonAction)
                    },
                ) {
                    Text(caAlertUiStateDialogUiState.confirmButtonText)
                }
            }
        },
        dismissButton = {
            if (caAlertUiStateDialogUiState.dismissButtonText.isNotEmpty()) {
                TextButton(
                    onClick = {
                        onAction(caAlertUiStateDialogUiState.onDismissButtonAction)
                    }
                ) {
                    Text(caAlertUiStateDialogUiState.dismissButtonText)
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    )
}

@Preview(
    showBackground = true,
)
@Composable
private fun PreviewCaDialogScreen() {
    CaDialogScreen(
        caAlertUiStateDialogUiState = CaAlertUiStateDialogUiState.Default.copy(
            title = "title",
            message = "A dialog is a type of modal window that appears in front of app content to provide critical" +
                    "information, or ask for a decision to be made.",
            confirmButtonText = "Confirm",
            dismissButtonText = "Dismiss",
        ),
        onAction = {},
    )
}
