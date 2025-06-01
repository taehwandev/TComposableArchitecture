package tech.thdev.composable.architecture.router.system.viewmodel

import app.cash.turbine.test
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.router.system.fake.FakeActivityRoute
import tech.thdev.composable.architecture.router.system.fake.FakeNavigation
import tech.thdev.composable.architecture.router.system.internal.route.InternalNavigator
import tech.thdev.composable.architecture.router.system.internal.route.InternalRoute
import tech.thdev.composable.architecture.router.system.internal.visitor.InternalActivityRouteMapper

internal class InternalRouteViewModelTest {

    private val fakeActivityRoute = FakeActivityRoute()
    private val journeyMapper = InternalActivityRouteMapper(mapper = mapOf(FakeActivityRoute::class to fakeActivityRoute))

    private val navigator = mock<InternalNavigator>()

    private val viewModel = InternalRouteViewModel(
        navigator = navigator,
        journeyMapper = journeyMapper,
    )

    @Test
    fun `test sideEffect`() = runTest {
        val mockChannel = Channel<InternalRoute>(Channel.BUFFERED)
        whenever(navigator.channel).thenReturn(mockChannel)

        viewModel.sideEffect
            .test {
                // Move navigation
                mockChannel.send(
                    InternalRoute.Navigation(
                        navigationRoute = FakeNavigation,
                        saveState = true,
                    )
                )
                Assert.assertEquals(InternalRouteSideEffect.Navigate(navigationRoute = FakeNavigation, saveState = true), awaitItem())

                // Move activity
                mockChannel.send(
                    InternalRoute.Activity(
                        activityRoute = FakeActivityRoute::class,
                    )
                )
                Assert.assertEquals(InternalRouteSideEffect.NavigateActivity(activityRoute = fakeActivityRoute, argumentMap = emptyMap()), awaitItem())


                // Back test
                mockChannel.send(InternalRoute.NavigateBack)
                Assert.assertEquals(InternalRouteSideEffect.NavigateBack, awaitItem())

                cancelAndConsumeRemainingEvents()
            }
    }
}
