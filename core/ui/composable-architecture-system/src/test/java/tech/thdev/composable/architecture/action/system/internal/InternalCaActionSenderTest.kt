package tech.thdev.composable.architecture.action.system.internal

import android.os.Build
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import tech.thdev.composable.architecture.action.system.CaAction

@Config(sdk = [Build.VERSION_CODES.VANILLA_ICE_CREAM])
@RunWith(RobolectricTestRunner::class)
class InternalCaActionSenderTest {

    private val flowAction = InternalCaAction()

    @Test
    fun `test flowAction`() = runTest {
        flowAction.flowAction()
            .test {
                expectNoEvents()

                // If the action is none, do not process it.
                flowAction.send(CaAction.None)
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
