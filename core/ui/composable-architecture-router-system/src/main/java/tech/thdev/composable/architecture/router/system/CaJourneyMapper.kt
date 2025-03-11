package tech.thdev.composable.architecture.router.system

import androidx.annotation.VisibleForTesting
import tech.thdev.composable.architecture.router.system.navigation.CaActivityRoute

/**
 * Dagger or hilt Map
 */
class CaJourneyMapper(
    @get:VisibleForTesting internal val mapper: Map<Class<out CaActivityRoute>, CaActivityRoute>,
) {

    /**
     * Find and return ActivityRoute from the data stored in Mapper.
     */
    fun getJourneyOrNull(journeyKClass: Class<*>): CaActivityRoute? =
        synchronized(mapper) {
            mapper[journeyKClass]
        }
}
