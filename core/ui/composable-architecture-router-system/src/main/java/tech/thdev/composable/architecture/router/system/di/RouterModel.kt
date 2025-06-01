package tech.thdev.composable.architecture.router.system.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import tech.thdev.composable.architecture.router.system.Navigator
import tech.thdev.composable.architecture.router.system.internal.route.InternalNavigator
import tech.thdev.composable.architecture.router.system.internal.route.InternalNavigatorImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class RouterModel {

    @Binds
    @ActivityRetainedScoped
    abstract fun provideNavigator(
        navigator: InternalNavigatorImpl
    ): Navigator

    @Binds
    @ActivityRetainedScoped
    abstract fun provideInternalNavigator(
        navigator: InternalNavigatorImpl
    ): InternalNavigator
}
