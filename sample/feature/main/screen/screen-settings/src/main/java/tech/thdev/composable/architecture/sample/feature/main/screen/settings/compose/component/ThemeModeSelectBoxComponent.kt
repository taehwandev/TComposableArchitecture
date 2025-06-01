package tech.thdev.composable.architecture.sample.feature.main.screen.settings.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.thdev.composable.architecture.action.system.compose.LocalActionSenderOwner
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.R
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.SettingsAction
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.model.SettingsUiState

@Composable
internal fun ThemeModeSelectBox(
    mode: SettingsUiState.Mode,
    modifier: Modifier = Modifier,
) {
    val actionSender = LocalActionSenderOwner.current

    ThemeModeSelectBox(
        mode = mode,
        onClick = {
            actionSender?.send(SettingsAction.ThemeModeChange(it))
        },
        modifier = modifier
    )
}

@Composable
internal fun ThemeModeSelectBox(
    mode: SettingsUiState.Mode,
    onClick: (mode: SettingsUiState.Mode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            DarkOnOff(
                selected = rememberUpdatedState(mode == SettingsUiState.Mode.AUTO).value,
                title = "Auto",
                onClick = {
                    onClick(SettingsUiState.Mode.AUTO)
                },
                res = R.drawable.img_dark_auto,
                modifier = Modifier
                    .weight(1f)
            )
            DarkOnOff(
                selected = rememberUpdatedState(mode == SettingsUiState.Mode.DARK).value,
                title = "Dark",
                onClick = {
                    onClick(SettingsUiState.Mode.DARK)
                },
                res = R.drawable.img_dark_on,
                modifier = Modifier
                    .weight(1f)
            )
            DarkOnOff(
                selected = rememberUpdatedState(mode == SettingsUiState.Mode.LIGHT).value,
                title = "Light",
                onClick = {
                    onClick(SettingsUiState.Mode.LIGHT)
                },
                res = R.drawable.img_dark_off,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun PreviewThemeModeSelectBox() {
    var mode by remember { mutableStateOf(SettingsUiState.Mode.AUTO) }
    ThemeModeSelectBox(
        mode = mode,
        onClick = {
            mode = it
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}
