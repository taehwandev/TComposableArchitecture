package tech.thdev.composable.architecture.sample.feature.main.screen.search

internal sealed interface SearchSideEffect {

    data object ShowToast : SearchSideEffect
}
