package tech.thdev.composable.architecture.alert.system

import android.os.Build
import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.alert.system.model.CaAlertUiState

@Config(sdk = [Build.VERSION_CODES.VANILLA_ICE_CREAM])
@RunWith(RobolectricTestRunner::class)
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
            onConfirmButtonAction = CaAction.None,
            dismissButtonText = "dismissButtonText",
            onDismissButtonAction = CaAction.None,
            onDismissRequest = CaAction.None,
        )
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        caAlertViewModel.flowAction.test {
            val convert = CaAlertUiState.Dialog.Default.copy(
                title = "title",
                message = "message",
                confirmButtonText = "confirmButtonText",
                dismissButtonText = "dismissButtonText",
            )
            Assert.assertEquals(CaAction.None, awaitItem())
            Assert.assertEquals(convert, caAlertViewModel.alertUiState.value)

            verify(flowCaActionStream).nextAction(CaAction.None)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test Snack`() = runTest {
        val mockItem = CaAlertAction.Snack(
            message = "message",
            actionLabel = "actionLabel",
            onAction = CaAction.None,
            onDismiss = CaAction.None,
        )
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        caAlertViewModel.flowAction.test {
            val convert = CaAlertUiState.Snack.Default.copy(
                message = "message",
                actionLabel = "actionLabel",
            )
            Assert.assertEquals(CaAction.None, awaitItem())
            Assert.assertEquals(convert, caAlertViewModel.alertUiState.value)

            verify(flowCaActionStream).nextAction(CaAction.None)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test send`() {
        caAlertViewModel.send(CaAction.None)
        Assert.assertNull(caAlertViewModel.alertUiState.value)
        verify(flowCaActionStream).nextAction(CaAction.None)
    }
}
