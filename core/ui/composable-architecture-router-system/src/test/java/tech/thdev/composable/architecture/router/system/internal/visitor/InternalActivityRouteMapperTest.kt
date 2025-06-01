package tech.thdev.composable.architecture.router.system.internal.visitor

import org.junit.Assert
import org.junit.Test
import tech.thdev.composable.architecture.router.system.fake.FakeActivityRoute

internal class InternalActivityRouteMapperTest {

    private val fakeActivityRoute = FakeActivityRoute()
    private val journeyMapper = InternalActivityRouteMapper(mapper = mapOf(FakeActivityRoute::class to fakeActivityRoute))

    @Test
    fun `test initData`() {
        Assert.assertEquals(mapOf(FakeActivityRoute::class to fakeActivityRoute), journeyMapper.mapper)
    }

    @Test
    fun `test getJourneyOrNull`() {
        Assert.assertEquals(fakeActivityRoute, journeyMapper.getJourneyOrNull(FakeActivityRoute::class))
    }
}
