package tech.thdev.composable.architecture.sample.feature.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.router.system.CaRouterAction
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter
import tech.thdev.composable.architecture.sample.feature.detail.api.model.DetailData
import tech.thdev.composable.architecture.sample.feature.detail.model.DetailUiState

class DetailViewModelTest {

    private val flowCaActionStream = mock<FlowCaActionStream>()
    private val savedStateHandle = mock<SavedStateHandle>()

    private val viewModel = DetailViewModel(
        flowCaActionStream = flowCaActionStream,
        savedStateHandle = savedStateHandle,
    )

    private val action = MutableSharedFlow<Action>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    @Before
    fun setUp() {
        whenever(flowCaActionStream.flowAction()).thenReturn(action)
    }

    @Test
    fun `test initData`() {
        Assert.assertEquals(DetailUiState.Default, viewModel.detailUiState.value)
    }

    @Test
    fun `test Task`() = runTest {
        viewModel.flowAction.test {
            expectNoEvents()

            // Task 실행
            whenever(savedStateHandle.get<DetailData>(DetailActivityRouter.PUT_DATA)).thenReturn(DetailData(text = "message"))

            action.tryEmit(Action.Task)

            val convert = DetailUiState(message = "message")
            Assert.assertEquals(convert, viewModel.detailUiState.value)

            verify(savedStateHandle).get<DetailData>(DetailActivityRouter.PUT_DATA)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test MoveBack`() = runTest {
        val mockItem = Action.MoveBack
        whenever(flowCaActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())
            verify(flowCaActionStream).nextAction(CaRouterAction.MoveActivityBack)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
