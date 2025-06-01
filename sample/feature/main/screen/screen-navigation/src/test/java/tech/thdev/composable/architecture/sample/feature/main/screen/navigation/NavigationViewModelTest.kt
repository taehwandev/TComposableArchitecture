package tech.thdev.composable.architecture.sample.feature.main.screen.navigation

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.router.system.Navigator
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.model.NavigationUiState
import tech.thdev.composable.architecture.sample.feature.main.screen.setting.api.SettingsRoute

internal class NavigationViewModelTest {

    private val flowActionStream = mock<FlowActionStream>()
    private val navigator = mock<Navigator>()

    private val viewModel = NavigationViewModel(
        flowActionStream = flowActionStream,
        navigator = navigator,
    )

    @Test
    fun `test initData`() {
        Assert.assertEquals(NavigationUiState.Default, viewModel.navigationUiState.value)
    }

    @Test
    fun `test SwitchNavigation`() = runTest {
        val mock = NavigationAction.SwitchNavigation(
            navItem = NavigationUiState.NavItem.Setting,
        )
        whenever(flowActionStream.flowAction()).thenReturn(flowOf(mock))

        viewModel.flowAction
            .test {
                Assert.assertEquals(
                    NavigationUiState.NavItem.Setting,
                    viewModel.navigationUiState.value.selectNav
                )
                verify(navigator).navigate(
                    navigationRoute = SettingsRoute,
                    saveState = true,
                )

                verify(flowActionStream).flowAction()

                cancelAndIgnoreRemainingEvents()
            }
    }
}
