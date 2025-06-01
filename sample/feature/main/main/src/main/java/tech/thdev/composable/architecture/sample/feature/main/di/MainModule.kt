package tech.thdev.composable.architecture.sample.feature.main.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import tech.thdev.composable.architecture.router.system.di.RouteKey
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import tech.thdev.composable.architecture.sample.feature.main.MainActivityRouteImpl
import tech.thdev.composable.architecture.sample.feature.main.api.MainActivityRouter

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainModule {

    @Binds
    @IntoMap
    @RouteKey(MainActivityRouter::class)
    abstract fun bindMainActivityRoute(
        mainActivityRoute: MainActivityRouteImpl,
    ): ActivityRoute
}
