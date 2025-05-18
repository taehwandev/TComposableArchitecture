package tech.thdev.composable.architecture.router.system

import androidx.annotation.VisibleForTesting
import tech.thdev.composable.architecture.router.system.navigation.ActivityRoute

/**
 * Dagger or hilt Map
 */
class CaJourneyMapper(
    @get:VisibleForTesting internal val mapper: Map<Class<out ActivityRoute>, ActivityRoute>,
) {

    /**
     * Find and return ActivityRoute from the data stored in Mapper.
     */
    fun getJourneyOrNull(journeyKClass: Class<*>): ActivityRoute? =
        synchronized(mapper) {
            mapper[journeyKClass]
        }
}
