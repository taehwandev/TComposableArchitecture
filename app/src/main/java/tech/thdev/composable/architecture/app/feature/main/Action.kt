package tech.thdev.composable.architecture.app.feature.main

import tech.thdev.composable.architecture.action.system.CaAction

sealed interface Action : CaAction {

    data object Send : Action
}
