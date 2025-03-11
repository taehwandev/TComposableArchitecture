package tech.thdev.composable.architecture.base.mock

import tech.thdev.composable.architecture.action.system.internal.InternalCaAction
import tech.thdev.composable.architecture.base.CaViewModel

internal class MockViewModel : CaViewModel<MockAction>(
    flowCaActionStream = InternalCaAction(),
    actionClass = MockAction::class,
) {

    var taskClick = false

    var clickEvent = false

    override suspend fun reducer(action: MockAction) {
        println(action)
        when (action) {
            MockAction.Task -> {
                taskClick = true
            }

            MockAction.ClickEvent -> {
                clickEvent = true
            }
        }
    }
}
