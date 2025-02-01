package tech.thdev.composable.architecture.app.feature.main

import tech.thdev.composable.architecture.action.system.CaSideEffect

sealed interface SideEffect : CaSideEffect {

    data object ShowToast : SideEffect
}
