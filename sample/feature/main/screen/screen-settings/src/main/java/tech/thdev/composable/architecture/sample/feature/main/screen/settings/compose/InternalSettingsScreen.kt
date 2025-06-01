package tech.thdev.composable.architecture.sample.feature.main.screen.settings.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.thdev.composable.architecture.action.system.compose.ActionSenderCompositionLocalProvider
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.SettingsViewModel
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.compose.component.ThemeModeSelectBox
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.model.SettingsUiState

@Composable
internal fun InternalSettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    ActionSenderCompositionLocalProvider(settingsViewModel) {
        val settingsUiState by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()

        InternalSettingsScreen(
            onThemeModeSelectBox = { modifier ->
                ThemeModeSelectBox(
                    mode = settingsUiState.mode,
                    modifier = modifier
                )
            },
        )
    }
}

@Composable
private fun InternalSettingsScreen(
    onThemeModeSelectBox: @Composable (modifier: Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        onThemeModeSelectBox(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInternalSettingsScreen() {
    var uiState by remember { mutableStateOf(SettingsUiState.Default) }
    InternalSettingsScreen(
        onThemeModeSelectBox = { modifier ->
            ThemeModeSelectBox(
                mode = uiState.mode,
                onClick = { mode ->
                    uiState = SettingsUiState(mode = mode)
                },
                modifier = modifier
            )
        },
        modifier = Modifier
            .fillMaxSize()
    )
}
