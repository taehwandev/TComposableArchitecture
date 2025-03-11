package tech.thdev.composable.architecture.action.system.internal

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import tech.thdev.composable.architecture.action.system.CaAction

class InternalCaActionTest {

    private val flowAction = InternalCaAction()

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

        private val SomeAction = object : CaAction {}

        private val NextAction = object : CaAction {}
    }
}
