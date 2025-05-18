package tech.thdev.composable.architecture.router.system.internal

import android.os.Parcelable
import tech.thdev.composable.architecture.router.system.navigation.ActivityRoute
import tech.thdev.composable.architecture.router.system.navigation.CaNavigationRoute

internal sealed interface InternalCaSideEffect {

    data class MoveNavigation(
        val navigationRoute: CaNavigationRoute,
    ) : InternalCaSideEffect

    data object MoveNavigationBack : InternalCaSideEffect

    data class MoveActivityVisit(
        val activityRoute: ActivityRoute,
        val argumentMap: Map<String, Parcelable>,
    ) : InternalCaSideEffect

    data object MoveActivityBack : InternalCaSideEffect
}
