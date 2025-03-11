package tech.thdev.composable.architecture.base.mock

import tech.thdev.composable.architecture.action.system.CaAction

sealed interface MockAction : CaAction {

    data object Task : MockAction

    data object ClickEvent : MockAction
}
