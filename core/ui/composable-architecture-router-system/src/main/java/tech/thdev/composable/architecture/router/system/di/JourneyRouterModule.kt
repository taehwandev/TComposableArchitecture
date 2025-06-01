package tech.thdev.composable.architecture.router.system.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.thdev.composable.architecture.router.system.internal.visitor.InternalActivityRouteMapper
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object JourneyRouterModule {

    @Provides
    @Singleton
    fun providerInternalActivityRouteMapper(
        map: Map<Class<out ActivityRoute>, @JvmSuppressWildcards ActivityRoute>,
    ): InternalActivityRouteMapper =
        InternalActivityRouteMapper(map)
}
