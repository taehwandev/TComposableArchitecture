package tech.thdev.composable.architecture.router.system

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import tech.thdev.composable.architecture.lifecycle.LaunchedLifecycleViewModel
import tech.thdev.composable.architecture.lifecycle.collectLifecycleEvent
import tech.thdev.composable.architecture.router.system.internal.InternalCaRouterViewModel
import tech.thdev.composable.architecture.router.system.internal.InternalCaSideEffect


@Composable
fun CaLaunchRouter(
    navHostController: NavHostController? = null,
) {
    InternalCaLaunchRouter(
        navHostController = navHostController,
    )
}

@Composable
private fun InternalCaLaunchRouter(
    navHostController: NavHostController? = null,
    internalRouterViewModel: InternalCaRouterViewModel = hiltViewModel(),
) {
    val activity = LocalActivity.current

    internalRouterViewModel.sideEffect.collectLifecycleEvent { sideEffect ->
        when (sideEffect) {
            is InternalCaSideEffect.MoveNavigation -> {
                navHostController?.let { navigation ->
                    navigation.navigate(sideEffect.navigationRoute) {
                        navigation.graph.findStartDestination().route?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        restoreState = true
                    }
                }
            }

            is InternalCaSideEffect.MoveNavigationBack -> {
                navHostController?.popBackStack()
            }

            is InternalCaSideEffect.MoveActivityVisit -> {
                activity?.startActivity(
                    sideEffect.activityRoute.getActivity(activity).apply {
                        sideEffect.argumentMap.entries.forEach { (key, value) ->
                            putExtra(key, value)
                        }
                    }
                )
            }

            is InternalCaSideEffect.MoveActivityBack -> {
                activity?.finish()
            }
        }
    }

    LaunchedLifecycleViewModel(
        viewModel = internalRouterViewModel,
    )
}
