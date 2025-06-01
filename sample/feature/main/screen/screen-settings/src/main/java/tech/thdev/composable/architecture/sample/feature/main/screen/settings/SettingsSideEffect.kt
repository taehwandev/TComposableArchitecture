package tech.thdev.composable.architecture.sample.feature.main.screen.settings

sealed interface SettingsSideEffect {

    data object ShowToast : SettingsSideEffect
}
