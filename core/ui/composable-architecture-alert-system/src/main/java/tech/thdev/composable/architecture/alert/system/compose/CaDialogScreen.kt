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
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiState

@Composable
internal fun CaDialogScreen(
    caAlertUiState: CaAlertUiState.Dialog,
    onAction: (nextAction: CaAction) -> Unit,
) {
    AlertDialog(
        icon = {
            if (caAlertUiState.icon != View.NO_ID) {
                Icon(
                    painter = painterResource(id = caAlertUiState.icon),
                    contentDescription = null,
                )
            }
        },
        title = {
            if (caAlertUiState.title.isNotEmpty()) {
                Text(text = caAlertUiState.title)
            }
        },
        text = {
            if (caAlertUiState.message.isNotEmpty()) {
                Text(text = caAlertUiState.message)
            }
        },
        onDismissRequest = {
            onAction(caAlertUiState.onDismissRequest)
        },
        confirmButton = {
            if (caAlertUiState.confirmButtonText.isNotEmpty()) {
                TextButton(
                    onClick = {
                        onAction(caAlertUiState.onConfirmButtonAction)
                    },
                ) {
                    Text(caAlertUiState.confirmButtonText)
                }
            }
        },
        dismissButton = {
            if (caAlertUiState.dismissButtonText.isNotEmpty()) {
                TextButton(
                    onClick = {
                        onAction(caAlertUiState.onDismissButtonAction)
                    }
                ) {
                    Text(caAlertUiState.dismissButtonText)
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
        caAlertUiState = CaAlertUiState.Dialog.Default.copy(
            title = "title",
            message = "A dialog is a type of modal window that appears in front of app content to provide critical" +
                    "information, or ask for a decision to be made.",
            confirmButtonText = "Confirm",
            dismissButtonText = "Dismiss",
        ),
        onAction = {},
    )
}
