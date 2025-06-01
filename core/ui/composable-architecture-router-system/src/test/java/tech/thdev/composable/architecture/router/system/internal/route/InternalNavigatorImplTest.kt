package tech.thdev.composable.architecture.router.system.internal.route

import app.cash.turbine.test
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import tech.thdev.composable.architecture.router.system.fake.FakeActivityRoute
import tech.thdev.composable.architecture.router.system.fake.FakeNavigation

internal class InternalNavigatorImplTest {

    private val navigator = InternalNavigatorImpl()

    @Test
    fun `test navigate`() = runTest {
        navigator.channel.receiveAsFlow()
            .test {
                // fake route test
                navigator.navigate(FakeNavigation)
                Assert.assertEquals(InternalRoute.Navigation(FakeNavigation, false), awaitItem())

                // Back test
                navigator.navigateBack()
                Assert.assertEquals(InternalRoute.NavigateBack, awaitItem())

                // fake route activity
                navigator.navigate(FakeActivityRoute::class)
                Assert.assertEquals(InternalRoute.Activity(FakeActivityRoute::class), awaitItem())

                cancelAndConsumeRemainingEvents()
            }
    }
}
