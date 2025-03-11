package tech.thdev.composable.architecture.sample.feature.main.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import tech.thdev.composable.architecture.router.system.di.CaRouterKey
import tech.thdev.composable.architecture.router.system.navigation.CaActivityRoute
import tech.thdev.composable.architecture.sample.feature.main.MainActivityRouteImpl
import tech.thdev.composable.architecture.sample.feature.main.api.MainActivityRouter

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    @IntoMap
    @CaRouterKey(MainActivityRouter::class)
    abstract fun bindMainActivityRoute(
        mainActivityRoute: MainActivityRouteImpl,
    ): CaActivityRoute
}
