package tech.thdev.composable.architecture.app.feature.main

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.alert.system.CaAlertAction
import tech.thdev.composable.architecture.app.R

internal class MainViewModelTest {

    private val flowCaActionStream = mock<FlowCaActionStream>()

    private val viewModel = MainViewModel(
        flowCaActionStream = flowCaActionStream,
    )

    @Test
    fun `test ShowToast`() = runTest {
        viewModel.sideEffect.awaitTest {
            Assert.assertEquals(SideEffect.ShowToast, it)
        }

        val mockItem = Action.ShowToast
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test ShowAlert`() = runTest {
        val mockItem = Action.ShowAlert(
            icon = R.drawable.baseline_info_24,
            title = "title",
            message = "message",
            confirmButtonText = "confirmButtonText",
            dismissButtonText = "dismissButtonText",
        )
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())

            verify(flowCaActionStream).nextAction(
                CaAlertAction.Dialog(
                    icon = R.drawable.baseline_info_24,
                    title = "title",
                    message = "message",
                    confirmButtonText = "confirmButtonText",
                    onConfirmButtonAction = CaAlertAction.Snack(
                        message = "Confirm",
                    ),
                    dismissButtonText = "dismissButtonText",
                    onDismissButtonAction = CaAlertAction.Snack(
                        message = "Dismiss",
                    ),
                )
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}
