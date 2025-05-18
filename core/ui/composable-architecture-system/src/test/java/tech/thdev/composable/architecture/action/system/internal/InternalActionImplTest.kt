package tech.thdev.composable.architecture.action.system.internal

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import tech.thdev.composable.architecture.action.system.Action

class InternalActionImplTest {

    private val flowAction = InternalActionImpl()

    @Test
    fun `test flowAction`() = runTest {
        flowAction.flowAction()
            .test {
                expectNoEvents()

                flowAction.send(SomeAction)
                Assert.assertEquals(SomeAction, awaitItem())

                flowAction.nextAction(NextAction)
                Assert.assertEquals(NextAction, awaitItem())

                cancelAndIgnoreRemainingEvents()
            }
    }

    companion object {

        private val SomeAction = object : Action {}

        private val NextAction = object : Action {}
    }
}
