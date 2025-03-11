package tech.thdev.composable.architecture.alert.system

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiState

internal class DialogViewModelTest {

    private val flowCaActionStream = mock<FlowCaActionStream>()

    private val caAlertViewModel = CaAlertViewModel(
        flowCaActionStream = flowCaActionStream,
    )

    @Test
    fun `test initData`() {
        Assert.assertNull(caAlertViewModel.alertUiState.value)
    }

    @Test
    fun `test Alert`() = runTest {
        val mockItem = CaAlertAction.Dialog(
            title = "title",
            message = "message",
            confirmButtonText = "confirmButtonText",
            onConfirmButtonAction = CaAlertAction.None,
            dismissButtonText = "dismissButtonText",
            onDismissButtonAction = CaAlertAction.None,
            onDismissRequest = CaAlertAction.None,
        )
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        caAlertViewModel.flowAction.test {
            val convert = CaAlertUiState.Dialog.Default.copy(
                title = "title",
                message = "message",
                confirmButtonText = "confirmButtonText",
                dismissButtonText = "dismissButtonText",
            )
            Assert.assertEquals(mockItem, awaitItem())
            Assert.assertEquals(convert, caAlertViewModel.alertUiState.value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test Snack`() = runTest {
        val mockItem = CaAlertAction.Snack(
            message = "message",
            actionLabel = "actionLabel",
            onAction = CaAlertAction.None,
            onDismiss = CaAlertAction.None,
        )
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        caAlertViewModel.flowAction.test {
            val convert = CaAlertUiState.Snack.Default.copy(
                message = "message",
                actionLabel = "actionLabel",
            )
            Assert.assertEquals(mockItem, awaitItem())
            Assert.assertEquals(convert, caAlertViewModel.alertUiState.value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test send`() {
        caAlertViewModel.send(CaAlertAction.None)
        Assert.assertNull(caAlertViewModel.alertUiState.value)
        verify(flowCaActionStream).nextAction(CaAlertAction.None)
    }
}
