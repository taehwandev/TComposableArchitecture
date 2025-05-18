package tech.thdev.composable.architecture.router.system

import android.os.Parcelable
import tech.thdev.composable.architecture.action.system.Action
import tech.thdev.composable.architecture.router.system.navigation.ActivityRoute
import tech.thdev.composable.architecture.router.system.navigation.CaNavigationRoute
import kotlin.reflect.KClass

sealed interface CaRouterAction : Action {

    data class MoveNavigation(
        val navigationRoute: CaNavigationRoute,
    ) : CaRouterAction

    data object MoveNavigationBack : CaRouterAction

    data class MoveActivityVisit<T : ActivityRoute>(
        val activityRoute: KClass<T>,
        val argumentMap: Map<String, Parcelable> = emptyMap(),
    ) : CaRouterAction

    data object MoveActivityBack : CaRouterAction
}
