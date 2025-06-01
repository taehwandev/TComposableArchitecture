package tech.thdev.composable.architecture.sample.feature.main.screen.search

import tech.thdev.composable.architecture.action.system.Action

sealed interface MainAction : Action {

    data object ShowToast : MainAction

    data class ShowDetail(
        val message: String,
    ) : MainAction

    data class ShowAlert(
        val icon: Int,
        val title: String,
        val message: String,
        val confirmButtonText: String,
        val dismissButtonText: String,
    ) : MainAction
}
