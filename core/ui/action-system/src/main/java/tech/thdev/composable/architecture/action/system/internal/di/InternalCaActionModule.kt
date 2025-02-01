package tech.thdev.composable.architecture.action.system.internal.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.thdev.composable.architecture.action.system.CaActionSender
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.action.system.internal.InternalCaAction
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InternalCaActionModule {

    @Binds
    @Singleton
    abstract fun bindFlowCaActionStream(
        internalCaAction: InternalCaAction,
    ): FlowCaActionStream

    @Binds
    @Singleton
    abstract fun bindCaActionSender(
        internalCaAction: InternalCaAction,
    ): CaActionSender
}
