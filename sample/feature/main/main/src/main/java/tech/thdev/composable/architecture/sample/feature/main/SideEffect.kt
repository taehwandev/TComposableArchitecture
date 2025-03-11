package tech.thdev.composable.architecture.sample.feature.main

sealed interface SideEffect {

    data object ShowToast : SideEffect
}
