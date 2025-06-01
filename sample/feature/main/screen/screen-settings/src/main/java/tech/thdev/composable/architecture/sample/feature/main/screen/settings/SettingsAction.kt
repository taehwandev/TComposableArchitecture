package tech.thdev.composable.architecture.sample.feature.main.screen.settings

import tech.thdev.composable.architecture.action.system.Action
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.model.SettingsUiState

internal sealed interface SettingsAction : Action {

    data class ThemeModeChange(
        val mode: SettingsUiState.Mode,
    ) : SettingsAction
}
