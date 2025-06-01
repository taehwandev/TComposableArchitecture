package tech.thdev.composable.architecture.sample.feature.main.screen.settings

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.model.SettingsUiState

internal class SettingsViewModelTest {

    private val flowActionStream = mock<FlowActionStream>()

    private val viewModel = SettingsViewModel(
        flowActionStream = flowActionStream,
    )

    @Test
    fun `test initData`() {
        Assert.assertEquals(SettingsUiState.Default, viewModel.settingsUiState.value)
    }

    @Test
    fun `test ThemeModeChange`() = runTest {
        val mockItem = SettingsAction.ThemeModeChange(mode = SettingsUiState.Mode.DARK)
        whenever(flowActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction
            .test {
                Assert.assertEquals(
                    SettingsAction.ThemeModeChange(mode = SettingsUiState.Mode.DARK),
                    awaitItem()
                )

                Assert.assertEquals(
                    SettingsUiState(
                        mode = SettingsUiState.Mode.DARK,
                    ),
                    viewModel.settingsUiState.value
                )

                verify(flowActionStream).flowAction()

                cancelAndIgnoreRemainingEvents()
            }
    }
}
