package tech.thdev.composable.architecture.sample.feature.main.screen.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.thdev.composable.architecture.sample.feature.main.screen.setting.api.SettingsRoute
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.compose.InternalSettingsScreen

fun NavGraphBuilder.settingsNavGraph() {
    composable<SettingsRoute> {
        InternalSettingsScreen()
    }
}
