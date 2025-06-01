package tech.thdev.composable.architecture.action.system.base

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import tech.thdev.composable.architecture.action.system.mock.FakeAction
import tech.thdev.composable.architecture.action.system.mock.FakeViewModel

internal class ActionViewModelTest {

    private val viewModel = FakeViewModel()

    @Test
    fun `test initData`() {
        Assert.assertFalse(viewModel.taskClick)
        Assert.assertFalse(viewModel.clickEvent)
        Assert.assertTrue(viewModel.isFirst)
        Assert.assertNull(viewModel.flowActionJob)
    }

    @Test
    fun `test Action-Task`() = runTest {
        viewModel.flowAction.test {
            expectNoEvents()

            viewModel.nextAction(FakeAction.Task)
            Assert.assertTrue(viewModel.taskClick)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test Action-ClickEvent`() = runTest {
        viewModel.flowAction.test {
            expectNoEvents()

            viewModel.nextAction(FakeAction.ClickEvent)
            Assert.assertTrue(viewModel.clickEvent)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
