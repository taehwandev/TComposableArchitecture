package tech.thdev.composable.architecture.router.system.internal.visitor

import androidx.annotation.VisibleForTesting
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Dagger or hilt Map
 */
@Singleton
internal class InternalActivityRouteMapper @Inject constructor(
    @get:VisibleForTesting val mapper: Map<KClass<out ActivityRoute>, ActivityRoute>,
) {

    /**
     * Find and return ActivityRoute from the data stored in Mapper.
     */
    internal fun getJourneyOrNull(journeyKClass: KClass<*>): ActivityRoute? =
        synchronized(mapper) {
            mapper[journeyKClass]
        }
}
