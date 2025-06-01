package tech.thdev.composable.architecture.router.system

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import tech.thdev.composable.architecture.router.system.viewmodel.InternalRouteSideEffect
import tech.thdev.composable.architecture.router.system.viewmodel.InternalRouteViewModel

@Composable
fun LaunchedRouter(
    navHostController: NavHostController? = null,
) {
    InternalLaunchedRouter(
        navHostController = navHostController,
    )
}

@Composable
private fun InternalLaunchedRouter(
    navHostController: NavHostController? = null,
    internalRouterViewModel: InternalRouteViewModel = viewModel(),
) {
    val activity = LocalActivity.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(internalRouterViewModel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            internalRouterViewModel.sideEffect.collectLatest { sideEffect ->
                when (sideEffect) {
                    is InternalRouteSideEffect.NavigateBack -> {
                        if (navHostController?.previousBackStackEntry != null) {
                            navHostController.popBackStack()
                        } else {
                            activity?.finish()
                        }
                    }

                    is InternalRouteSideEffect.NavigateActivity -> {
                        activity?.startActivity(
                            sideEffect.activityRoute.getActivity(activity).apply {
                                sideEffect.argumentMap.entries.forEach { (key, value) ->
                                    putExtra(key, value)
                                }
                            }
                        )
                    }

                    is InternalRouteSideEffect.Navigate -> {
                        navHostController?.let { navigation ->
                            navigation.navigate(sideEffect.navigationRoute) {
                                navigation.graph.findStartDestination().route?.let {
                                    popUpTo(it) {
                                        saveState = sideEffect.saveState
                                    }
                                }
                                restoreState = sideEffect.saveState
                            }
                        }
                    }
                }
            }
        }
    }
}
