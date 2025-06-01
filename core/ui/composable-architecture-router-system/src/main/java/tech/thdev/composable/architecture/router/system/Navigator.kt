package tech.thdev.composable.architecture.router.system

import android.os.Parcelable
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import tech.thdev.composable.architecture.router.system.route.NavigationRoute
import kotlin.reflect.KClass

interface Navigator {

    suspend fun <T : ActivityRoute> navigate(
        activityRoute: KClass<T>,
        argumentMap: Map<String, Parcelable> = emptyMap(),
    )

    suspend fun navigate(
        navigationRoute: NavigationRoute,
        saveState: Boolean = false,
    )

    suspend fun navigateBack()
}
