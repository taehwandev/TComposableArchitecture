package tech.thdev.composable.architecture.router.system.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import tech.thdev.composable.architecture.router.system.internal.route.InternalNavigator
import tech.thdev.composable.architecture.router.system.internal.route.InternalRoute
import tech.thdev.composable.architecture.router.system.internal.visitor.InternalActivityRouteMapper
import javax.inject.Inject

@HiltViewModel
internal class InternalRouteViewModel @Inject internal constructor(
    navigator: InternalNavigator,
    private val journeyMapper: InternalActivityRouteMapper,
) : ViewModel() {

    val sideEffect by lazy(LazyThreadSafetyMode.NONE) {
        navigator.channel.receiveAsFlow()
            .map { router ->
                when (router) {
                    is InternalRoute.Activity<*> -> {
                        journeyMapper.getJourneyOrNull(router.activityRoute)?.let {
                            InternalRouteSideEffect.NavigateActivity(
                                activityRoute = it,
                                argumentMap = router.argumentMap,
                            )
                        }
                    }

                    is InternalRoute.Navigation -> {
                        InternalRouteSideEffect.Navigate(
                            navigationRoute = router.navigationRoute,
                            saveState = router.saveState,
                        )
                    }

                    is InternalRoute.NavigateBack -> {
                        InternalRouteSideEffect.NavigateBack
                    }
                }
            }
            .filterNotNull()
    }
}
