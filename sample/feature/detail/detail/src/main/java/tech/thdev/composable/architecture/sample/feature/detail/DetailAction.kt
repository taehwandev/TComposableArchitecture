package tech.thdev.composable.architecture.sample.feature.detail

import tech.thdev.composable.architecture.action.system.Action

internal sealed interface DetailAction : Action {

    data object Task : DetailAction

    data object MoveBack : DetailAction
}
