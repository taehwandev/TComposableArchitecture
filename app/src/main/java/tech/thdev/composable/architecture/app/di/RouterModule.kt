package tech.thdev.composable.architecture.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.thdev.composable.architecture.router.system.CaJourneyMapper
import tech.thdev.composable.architecture.router.system.navigation.ActivityRoute
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RouterModule {

    @Provides
    @Singleton
    fun providerJourneyMapper(
        map: Map<Class<out ActivityRoute>, @JvmSuppressWildcards ActivityRoute>,
    ): CaJourneyMapper =
        CaJourneyMapper(map)
}
