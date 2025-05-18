package tech.thdev.composable.architecture.sample.feature.main.screen.main

sealed interface MainSideEffect {

    data object ShowToast : MainSideEffect
}
