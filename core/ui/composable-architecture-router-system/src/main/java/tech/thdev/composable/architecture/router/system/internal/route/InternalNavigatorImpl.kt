package tech.thdev.composable.architecture.router.system.internal.route

import android.os.Parcelable
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.channels.Channel
import tech.thdev.composable.architecture.router.system.Navigator
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import tech.thdev.composable.architecture.router.system.route.NavigationRoute
import javax.inject.Inject
import kotlin.reflect.KClass

@ActivityRetainedScoped
internal class InternalNavigatorImpl @Inject constructor() : Navigator, InternalNavigator {

    override val channel = Channel<InternalRoute>(Channel.BUFFERED)

    override suspend fun <T : ActivityRoute> navigate(activityRoute: KClass<T>, argumentMap: Map<String, Parcelable>) {
        channel.send(
            InternalRoute.Activity(
                activityRoute = activityRoute,
                argumentMap = argumentMap,
            )
        )
    }

    override suspend fun navigate(navigationRoute: NavigationRoute, saveState: Boolean) {
        channel.send(
            InternalRoute.Navigation(
                navigationRoute = navigationRoute,
                saveState = saveState,
            )
        )
    }

    override suspend fun navigateBack() {
        channel.send(InternalRoute.NavigateBack)
    }
}
