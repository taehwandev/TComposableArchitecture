package tech.thdev.composable.architecture.sample.feature.detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import tech.thdev.composable.architecture.router.system.di.RouteKey
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import tech.thdev.composable.architecture.sample.feature.detail.DetailActivityRouteImpl
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DetailModule {

    @Binds
    @IntoMap
    @RouteKey(DetailActivityRouter::class)
    abstract fun bindDetailActivityRoute(
        mainActivityRoute: DetailActivityRouteImpl,
    ): ActivityRoute
}
