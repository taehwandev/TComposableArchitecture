package tech.thdev.composable.architecture.sample.feature.main.screen.navigation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import tech.thdev.composable.architecture.action.system.compose.ActionSenderCompositionLocalProvider
import tech.thdev.composable.architecture.action.system.compose.LocalActionSenderOwner
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.NavigationAction
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.NavigationViewModel
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.model.NavigationUiState
import tech.thdev.composable.architecture.sample.feature.main.screen.search.searchNavGraph
import tech.thdev.composable.architecture.sample.feature.main.screen.settings.settingsNavGraph

@Composable
internal fun InternalNavigationScaffold(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel = hiltViewModel(),
) {
    ActionSenderCompositionLocalProvider(navigationViewModel) {
        val actionSender = LocalActionSenderOwner.current
        val navigationUiState by navigationViewModel.navigationUiState.collectAsStateWithLifecycle()

        InternalNavigationScreen(
            navigationUiState = navigationUiState,
            navController = navController,
            onClick = { navItem ->
                actionSender?.send(NavigationAction.SwitchNavigation(navItem))
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
private fun InternalNavigationScreen(
    navigationUiState: NavigationUiState,
    navController: NavHostController,
    onClick: (navItem: NavigationUiState.NavItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Composable Architecture example")
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navigationUiState.navigation.forEach { navItem ->
                    NavigationBarItem(
                        selected = navigationUiState.selectNav == navItem,
                        onClick = {
                            onClick(navItem)
                        },
                        label = {
                            Text(navItem.title)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(navItem.icon),
                                contentDescription = navItem.title,
                            )
                        },
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {
            NavHost(
                navController = navController,
                startDestination = navigationUiState.selectNav.route,
            ) {
                searchNavGraph()
                settingsNavGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInternalNavigationScreen() {
    InternalNavigationScreen(
        navigationUiState = NavigationUiState.Default,
        navController = rememberNavController(),
        onClick = {},
        modifier = Modifier
            .fillMaxSize()
    )
}
