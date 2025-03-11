package tech.thdev.composable.architecture.router.system

import android.os.Parcelable
import tech.thdev.composable.architecture.action.system.CaAction
import tech.thdev.composable.architecture.router.system.navigation.CaActivityRoute
import tech.thdev.composable.architecture.router.system.navigation.CaNavigationRoute
import kotlin.reflect.KClass

sealed interface CaRouterAction : CaAction {

    data class MoveNavigation(
        val navigationRoute: CaNavigationRoute,
    ) : CaRouterAction

    data object MoveNavigationBack : CaRouterAction

    data class MoveActivityVisit<T : CaActivityRoute>(
        val activityRoute: KClass<T>,
        val argumentMap: Map<String, Parcelable> = emptyMap(),
    ) : CaRouterAction

    data object MoveActivityBack : CaRouterAction
}
