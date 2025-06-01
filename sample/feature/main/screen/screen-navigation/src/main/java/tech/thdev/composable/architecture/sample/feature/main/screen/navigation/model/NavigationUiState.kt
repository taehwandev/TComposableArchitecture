package tech.thdev.composable.architecture.sample.feature.main.screen.navigation.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tech.thdev.composable.architecture.router.system.route.NavigationRoute
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.R
import tech.thdev.composable.architecture.sample.feature.main.screen.search.api.SearchRoute
import tech.thdev.composable.architecture.sample.feature.main.screen.setting.api.SettingsRoute

@Immutable
internal data class NavigationUiState(
    val selectNav: NavItem,
    val navigation: ImmutableList<NavItem>,
) {

    @Immutable
    internal sealed class NavItem(
        val title: String,
        val icon: Int,
        val route: NavigationRoute,
    ) {

        @Immutable
        data object Search : NavItem(
            title = "Search",
            icon = R.drawable.img_search,
            route = SearchRoute,
        )

        @Immutable
        data object Setting : NavItem(
            title = "Setting",
            icon = R.drawable.img_settings,
            route = SettingsRoute,
        )
    }

    companion object {

        val Default = NavigationUiState(
            selectNav = NavItem.Search,
            navigation = persistentListOf(
                NavItem.Search,
                NavItem.Setting,
            ),
        )
    }
}
