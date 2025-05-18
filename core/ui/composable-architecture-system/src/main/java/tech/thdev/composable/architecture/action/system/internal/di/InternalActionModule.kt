package tech.thdev.composable.architecture.action.system.internal.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tech.thdev.composable.architecture.action.system.ActionSender
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.action.system.internal.InternalActionImpl

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class InternalActionModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFlowActionStream(
        internalCaAction: InternalActionImpl,
    ): FlowActionStream

    @Binds
    @ViewModelScoped
    abstract fun bindCaActionSender(
        internalCaAction: InternalActionImpl,
    ): ActionSender
}
