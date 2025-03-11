package tech.thdev.composable.architecture.router.system

import org.junit.Assert
import org.junit.Test

class CaJourneyMapperTest {

    private val mockActivityRoute = MockActivityRoute()
    private val journeyMapper = CaJourneyMapper(mapper = mapOf(MockActivityRoute::class.java to mockActivityRoute))

    @Test
    fun `test initData`() {
        Assert.assertEquals(mapOf(MockActivityRoute::class.java to mockActivityRoute), journeyMapper.mapper)
    }

    @Test
    fun `test getJourneyOrNull`() {
        Assert.assertEquals(mockActivityRoute, journeyMapper.getJourneyOrNull(MockActivityRoute::class.java))
    }
}
