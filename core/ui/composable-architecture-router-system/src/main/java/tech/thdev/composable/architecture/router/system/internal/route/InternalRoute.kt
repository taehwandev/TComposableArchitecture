package tech.thdev.composable.architecture.router.system.internal.route

import android.os.Parcelable
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import tech.thdev.composable.architecture.router.system.route.NavigationRoute
import kotlin.reflect.KClass

sealed interface InternalRoute {

    data class Activity<T : ActivityRoute>(
        val activityRoute: KClass<T>,
        val argumentMap: Map<String, Parcelable> = emptyMap(),
    ) : InternalRoute

    data class Navigation(
        val navigationRoute: NavigationRoute,
        val saveState: Boolean = false,
    ) : InternalRoute

    data object NavigateBack : InternalRoute
}
