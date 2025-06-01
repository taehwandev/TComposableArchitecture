package tech.thdev.composable.architecture.sample.feature.main.screen.settings.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class SettingsUiState(
    val mode: Mode,
) {

    enum class Mode {
        LIGHT,
        DARK,
        AUTO,
    }

    companion object {

        val Default = SettingsUiState(
            mode = Mode.AUTO,
        )
    }
}
