package tech.thdev.composable.architecture.action.system.mock

import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.action.system.internal.InternalActionImpl

internal class FakeViewModel : ActionViewModel<FakeAction>(
    flowActionStream = InternalActionImpl(),
    actionClass = FakeAction::class,
) {

    var taskClick = false

    var clickEvent = false

    override suspend fun handleAction(action: FakeAction) {
        when (action) {
            FakeAction.Task -> {
                taskClick = true
            }

            FakeAction.ClickEvent -> {
                clickEvent = true
            }
        }
    }
}
