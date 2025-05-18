package tech.thdev.composable.architecture.action.system.mock

import tech.thdev.composable.architecture.action.system.Action

internal sealed interface FakeAction : Action {

    data object Task : FakeAction

    data object ClickEvent : FakeAction
}
