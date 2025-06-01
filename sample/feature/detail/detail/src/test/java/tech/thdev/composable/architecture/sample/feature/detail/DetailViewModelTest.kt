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
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.router.system.Navigator
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter
import tech.thdev.composable.architecture.sample.feature.detail.api.model.DetailData
import tech.thdev.composable.architecture.sample.feature.detail.model.DetailUiState

class DetailViewModelTest {

    private val flowActionStream = mock<FlowActionStream>()
    private val savedStateHandle = mock<SavedStateHandle>()
    private val navigator = mock<Navigator>()

    private val viewModel = DetailViewModel(
        flowActionStream = flowActionStream,
        savedStateHandle = savedStateHandle,
        navigator = navigator,
    )

    private val action = MutableSharedFlow<DetailAction>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    @Before
    fun setUp() {
        whenever(flowActionStream.flowAction()).thenReturn(action)
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

            action.tryEmit(DetailAction.Task)

            val convert = DetailUiState(message = "message")
            Assert.assertEquals(convert, viewModel.detailUiState.value)

            verify(savedStateHandle).get<DetailData>(DetailActivityRouter.PUT_DATA)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test MoveBack`() = runTest {
        val mockItem = DetailAction.MoveBack
        whenever(flowActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction.test {
            Assert.assertEquals(mockItem, awaitItem())
            verify(navigator).navigateBack()

            cancelAndIgnoreRemainingEvents()
        }
    }
}
