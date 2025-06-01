package tech.thdev.composable.architecture.sample.feature.main.screen.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.thdev.composable.architecture.sample.feature.main.screen.search.api.SearchRoute
import tech.thdev.composable.architecture.sample.feature.main.screen.search.compose.InternalSearchScreen

fun NavGraphBuilder.searchNavGraph() {
    composable<SearchRoute> {
        InternalSearchScreen()
    }
}
