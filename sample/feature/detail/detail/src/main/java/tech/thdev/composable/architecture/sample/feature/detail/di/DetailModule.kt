package tech.thdev.composable.architecture.sample.feature.detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import tech.thdev.composable.architecture.router.system.di.CaRouterKey
import tech.thdev.composable.architecture.router.system.navigation.ActivityRoute
import tech.thdev.composable.architecture.sample.feature.detail.DetailActivityRouteImpl
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DetailModule {

    @Binds
    @IntoMap
    @CaRouterKey(DetailActivityRouter::class)
    abstract fun bindDetailActivityRoute(
        mainActivityRoute: DetailActivityRouteImpl,
    ): ActivityRoute
}
