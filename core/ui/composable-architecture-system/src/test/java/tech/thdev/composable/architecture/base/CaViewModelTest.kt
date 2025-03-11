package tech.thdev.composable.architecture.base

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import tech.thdev.composable.architecture.base.mock.MockAction
import tech.thdev.composable.architecture.base.mock.MockViewModel

class CaViewModelTest {

    private val viewModel = MockViewModel()

    @Test
    fun `test initData`() {
        Assert.assertFalse(viewModel.taskClick)
        Assert.assertFalse(viewModel.clickEvent)
        Assert.assertNull(viewModel.flowActionJob)
    }

    @Test
    fun `test Action-Task`() = runTest {
        viewModel.flowAction.test {
            expectNoEvents()

            viewModel.nextAction(MockAction.Task)
            Assert.assertTrue(viewModel.taskClick)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test Action-ClickEvent`() = runTest {
        viewModel.flowAction.test {
            expectNoEvents()

            viewModel.nextAction(MockAction.ClickEvent)
            Assert.assertTrue(viewModel.clickEvent)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
