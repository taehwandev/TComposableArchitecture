package tech.thdev.composable.architecture.sample.feature.main.screen.main

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.sample.feature.main.awaitTest
import tech.thdev.composable.architecture.sample.resource.R

internal class MainViewModelTest {

    private val flowActionStream = mock<FlowActionStream>()

    private val viewModel = MainViewModel(
        flowActionStream = flowActionStream,
    )

    @Test
    fun `test ShowToast`() = runTest {
        viewModel.sideEffect.awaitTest {
            Assert.assertEquals(MainSideEffect.ShowToast, it)
        }

        val mockItem = MainAction.ShowToast
        whenever(flowActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test ShowAlert`() = runTest {
        val mockItem = MainAction.ShowAlert(
            icon = R.drawable.baseline_info_24,
            title = "title",
            message = "message",
            confirmButtonText = "confirmButtonText",
            dismissButtonText = "dismissButtonText",
        )
        whenever(flowActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())

//            verify(flowActionStream).nextAction(
//                CaAlertAction.ShowDialog(
//                    icon = R.drawable.baseline_info_24,
//                    title = "title",
//                    message = "message",
//                    confirmButtonText = "confirmButtonText",
//                    onConfirmButtonAction = CaAlertAction.ShowSnack(
//                        message = "Confirm",
//                    ),
//                    dismissButtonText = "dismissButtonText",
//                    onDismissButtonAction = CaAlertAction.ShowSnack(
//                        message = "Dismiss",
//                    ),
//                )
//            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test ShowDetail`() = runTest {
        val mockItem = MainAction.ShowDetail(
            message = "Show detail activity",
        )
        whenever(flowActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())

//            verify(flowActionStream).nextAction(
//                CaRouterAction.MoveActivityVisit(
//                    activityRoute = DetailActivityRouter::class,
//                    argumentMap = mapOf(
//                        DetailActivityRouter.PUT_DATA to DetailData(text = "Show detail activity")
//                    )
//                )
//            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}
