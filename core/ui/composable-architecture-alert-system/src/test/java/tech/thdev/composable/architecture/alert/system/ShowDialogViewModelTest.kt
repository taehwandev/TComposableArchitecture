package tech.thdev.composable.architecture.alert.system

import app.cash.turbine.test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiStateDialogUiState

internal class ShowDialogViewModelTest {

    private val flowCaActionStream = mock<FlowCaActionStream>()

    private val caAlertViewModel = CaAlertViewModel(
        flowCaActionStream = flowCaActionStream,
    )

    @Test
    fun `test initData`() {
        Assert.assertEquals(CaAlertUiStateDialogUiState.Default, caAlertViewModel.alertUiStateDialogUiState.value)
    }

    @Test
    fun `test ShowAlert`() = runTest {
        val mockItem = CaAlertAction.ShowDialog(
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
            val convert = CaAlertUiStateDialogUiState.Default.copy(
                title = "title",
                message = "message",
                confirmButtonText = "confirmButtonText",
                dismissButtonText = "dismissButtonText",
            )
            Assert.assertEquals(mockItem, awaitItem())
            Assert.assertEquals(convert, caAlertViewModel.alertUiStateDialogUiState.value)
            Assert.assertEquals(CaAlertSideEffect.ShowDialog, caAlertViewModel.sideEffect.first())

            verify(flowCaActionStream).flowAction()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test hideAlert`() = runTest {
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(CaAlertAction.HideDialog))

        caAlertViewModel.flowAction.test {
            Assert.assertEquals(CaAlertUiStateDialogUiState.Default, caAlertViewModel.alertUiStateDialogUiState.value)
            Assert.assertEquals(CaAlertSideEffect.HideDialog, caAlertViewModel.sideEffect.first())

            verify(flowCaActionStream).flowAction()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test Snack`() = runTest {
        val mockItem = CaAlertAction.ShowSnack(
            message = "message",
            actionLabel = "actionLabel",
            onAction = CaAlertAction.None,
            onDismiss = CaAlertAction.None,
        )
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        caAlertViewModel.flowAction.test {
            val convert = CaAlertSideEffect.ShowSnack.Default.copy(
                message = "message",
                actionLabel = "actionLabel",
            )
            Assert.assertEquals(mockItem, awaitItem())
            Assert.assertEquals(convert, caAlertViewModel.sideEffect.first())

            verify(flowCaActionStream).flowAction()

            cancelAndIgnoreRemainingEvents()
        }
    }
}
