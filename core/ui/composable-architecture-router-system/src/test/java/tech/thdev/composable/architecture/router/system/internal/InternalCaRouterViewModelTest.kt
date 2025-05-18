package tech.thdev.composable.architecture.router.system.internal

import app.cash.turbine.test
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.router.system.CaJourneyMapper
import tech.thdev.composable.architecture.router.system.CaRouterAction
import tech.thdev.composable.architecture.router.system.MockActivityRoute
import tech.thdev.composable.architecture.router.system.navigation.CaNavigationRoute

class InternalCaRouterViewModelTest {

    private val flowActionStream = mock<FlowActionStream>()

    private val mockActivityRoute = MockActivityRoute()
    private val journeyMapper = CaJourneyMapper(mapper = mapOf(MockActivityRoute::class.java to mockActivityRoute))

    private val viewModel = InternalActionRouterViewModel(
        flowActionStream = flowActionStream,
        journeyMapper = journeyMapper,
    )

    private val sharedFlow = MutableSharedFlow<CaRouterAction>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    @Before
    fun setUp() {
        whenever(flowActionStream.flowAction()).thenReturn(sharedFlow)
    }

    @Test
    fun `test flowAction MoveNavigation`() = runTest {
        viewModel.flowAction
            .test {
                expectNoEvents()

                // Move navigation
                val mockNavigationRoute = TempNavigationRoute(name = "move")
                sharedFlow.tryEmit(CaRouterAction.MoveNavigation(mockNavigationRoute))
                Assert.assertEquals(InternalCaSideEffect.MoveNavigation(mockNavigationRoute), viewModel.sideEffect.first())

                // Move back
                sharedFlow.tryEmit(CaRouterAction.MoveNavigationBack)
                Assert.assertEquals(InternalCaSideEffect.MoveNavigationBack, viewModel.sideEffect.first())

                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `test flowAction MoveActivity`() = runTest {
        viewModel.flowAction
            .test {
                expectNoEvents()

                // Move navigation
                sharedFlow.tryEmit(CaRouterAction.MoveActivityVisit(activityRoute = MockActivityRoute::class))
                Assert.assertEquals(
                    InternalCaSideEffect.MoveActivityVisit(activityRoute = mockActivityRoute, argumentMap = mapOf()),
                    viewModel.sideEffect.first()
                )

                // Move back
                sharedFlow.tryEmit(CaRouterAction.MoveActivityBack)
                Assert.assertEquals(InternalCaSideEffect.MoveActivityBack, viewModel.sideEffect.first())

                cancelAndIgnoreRemainingEvents()
            }
    }

    private data class TempNavigationRoute(val name: String) : CaNavigationRoute
}
