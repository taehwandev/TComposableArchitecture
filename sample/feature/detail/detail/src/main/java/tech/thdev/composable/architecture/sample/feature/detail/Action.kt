package tech.thdev.composable.architecture.sample.feature.detail

import tech.thdev.composable.architecture.action.system.CaAction

sealed interface Action : CaAction {

    data object Task : Action

    data object MoveBack : Action
}
