package tech.thdev.composable.architecture.sample.feature.main

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
import tech.thdev.composable.architecture.router.system.CaRouterAction
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter
import tech.thdev.composable.architecture.sample.feature.detail.api.model.DetailData
import tech.thdev.composable.architecture.sample.resource.R

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

    @Test
    fun `test ShowDetail`() = runTest {
        val mockItem = Action.ShowDetail(
            message = "Show detail activity",
        )
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())

            verify(flowCaActionStream).nextAction(
                CaRouterAction.MoveActivityVisit(
                    activityRoute = DetailActivityRouter::class,
                    argumentMap = mapOf(
                        DetailActivityRouter.PUT_DATA to DetailData(text = "Show detail activity")
                    )
                )
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}
