package tech.thdev.composable.architecture.sample.feature.main.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.compose.InternalNavigationScaffold

@Composable
fun NavigationScreen(
    navController: NavHostController,
) {
    InternalNavigationScaffold(
        navController = navController,
    )
}
