package tech.thdev.composable.architecture.sample.feature.main

import tech.thdev.composable.architecture.action.system.CaAction

sealed interface Action : CaAction {

    data object ShowToast : Action

    data class ShowDetail(
        val message: String,
    ) : Action

    data class ShowAlert(
        val icon: Int,
        val title: String,
        val message: String,
        val confirmButtonText: String,
        val dismissButtonText: String,
    ) : Action
}
