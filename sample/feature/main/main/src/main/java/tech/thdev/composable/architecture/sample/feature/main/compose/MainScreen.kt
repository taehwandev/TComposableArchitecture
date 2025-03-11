package tech.thdev.composable.architecture.sample.feature.main.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tech.thdev.composable.architecture.action.system.compose.LocalCaActionOwner
import tech.thdev.composable.architecture.action.system.send
import tech.thdev.composable.architecture.sample.feature.main.Action
import tech.thdev.composable.architecture.sample.resource.R

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val actionSender = LocalCaActionOwner.current

    Column(
        modifier = modifier
    ) {
        Button(
            onClick = actionSender.send(Action.ShowToast),
        ) {
            Text(
                text = "ShowToast",
            )
        }

        Button(
            onClick = actionSender.send(
                Action.ShowAlert(
                    icon = R.drawable.baseline_info_24,
                    title = "Info",
                    message = "A dialog is a type of modal window that appears in front of app content to provide critical" +
                            "information, or ask for a decision to be made.",
                    confirmButtonText = "Confirm",
                    dismissButtonText = "Dismiss",
                )
            ),
        ) {
            Text(
                text = "ShowAlert",
            )
        }

        Button(
            onClick = actionSender.send(Action.ShowDetail(message = "Show detail activity")),
        ) {
            Text(
                text = "Show Detail activity",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen() {
    MainScreen(
        modifier = Modifier
            .fillMaxSize()
    )
}
