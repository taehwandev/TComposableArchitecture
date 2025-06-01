package tech.thdev.composable.architecture.sample.feature.main.screen.navigation

import tech.thdev.composable.architecture.action.system.Action
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.model.NavigationUiState

internal sealed interface NavigationAction : Action {

    data class SwitchNavigation(val navItem: NavigationUiState.NavItem) : NavigationAction
}
