package tech.thdev.composable.architecture.sample.feature.main.screen.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.model.SettingsUiState
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    flowActionStream: FlowActionStream,
) : ActionViewModel<SettingsAction>(flowActionStream, SettingsAction::class) {

    private val _settingsUiState = MutableStateFlow(SettingsUiState.Default)
    val settingsUiState = _settingsUiState.asStateFlow()

    private val _sideEffect = Channel<SettingsSideEffect>(Channel.BUFFERED)
    internal val sideEffect = _sideEffect.receiveAsFlow()

    override suspend fun handleAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ThemeModeChange -> {
                _settingsUiState.update {
                    it.copy(
                        mode = action.mode,
                    )
                }
            }
        }
    }
}
