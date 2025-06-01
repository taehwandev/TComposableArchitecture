package tech.thdev.composable.architecture.sample.feature.main.screen.navigation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.router.system.Navigator
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.model.NavigationUiState
import javax.inject.Inject

@HiltViewModel
internal class NavigationViewModel @Inject constructor(
    flowActionStream: FlowActionStream,
    private val navigator: Navigator,
) : ActionViewModel<NavigationAction>(flowActionStream, NavigationAction::class) {

    private val _navigationUiState = MutableStateFlow(NavigationUiState.Default)
    val navigationUiState = _navigationUiState.asStateFlow()

    override suspend fun handleAction(action: NavigationAction) {
        when (action) {
            is NavigationAction.SwitchNavigation -> {
                navigator.navigate(
                    navigationRoute = action.navItem.route,
                    saveState = true,
                )

                _navigationUiState.update {
                    it.copy(
                        selectNav = action.navItem,
                    )
                }
            }
        }
    }
}
