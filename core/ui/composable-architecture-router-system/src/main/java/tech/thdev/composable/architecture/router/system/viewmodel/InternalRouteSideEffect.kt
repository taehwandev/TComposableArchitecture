package tech.thdev.composable.architecture.router.system.viewmodel

import android.os.Parcelable
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import tech.thdev.composable.architecture.router.system.route.NavigationRoute

internal sealed interface InternalRouteSideEffect {

    data class Navigate(
        val navigationRoute: NavigationRoute,
        val saveState: Boolean,
    ) : InternalRouteSideEffect

    data class NavigateActivity(
        val activityRoute: ActivityRoute,
        val argumentMap: Map<String, Parcelable>,
    ) : InternalRouteSideEffect

    data object NavigateBack : InternalRouteSideEffect
}
