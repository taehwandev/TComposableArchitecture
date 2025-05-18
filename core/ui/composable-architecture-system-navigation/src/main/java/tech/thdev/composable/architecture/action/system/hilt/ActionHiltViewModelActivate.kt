package tech.thdev.composable.architecture.action.system.hilt

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.action.system.lifecycle.LaunchedLifecycleActionViewModel

@Composable
inline fun <reified VM : ActionViewModel<*>> actionHiltViewModelActivate(): VM =
    hiltViewModel<VM>().also { viewModel ->
        LaunchedLifecycleActionViewModel(viewModel = viewModel)
    }
