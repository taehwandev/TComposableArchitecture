package tech.thdev.composable.architecture.action.system.hilt

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.action.system.lifecycle.LaunchedLifecycleActionViewModel

@Composable
inline fun <reified VM : ActionViewModel<*>> actionViewModelActivate(): VM =
    viewModel<VM>().also { viewModel ->
        LaunchedLifecycleActionViewModel(viewModel = viewModel)
    }
