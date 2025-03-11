package tech.thdev.composable.architecture.app.feature.main

sealed interface SideEffect {

    data object ShowToast : SideEffect
}
