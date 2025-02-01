package tech.thdev.composable.architecture.app.feature.main.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tech.thdev.composable.architecture.action.system.compose.LocalCaActionSenderOwner
import tech.thdev.composable.architecture.action.system.send
import tech.thdev.composable.architecture.app.feature.main.Action

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val actionSender = LocalCaActionSenderOwner.current

    Column(
        modifier = modifier
    ) {
        Button(
            onClick = actionSender.send(Action.Send),
        ) {
            Text(
                text = "Send",
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
