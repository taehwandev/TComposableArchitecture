package tech.thdev.composable.architecture.sample.feature.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.thdev.composable.architecture.action.system.FlowActionStream
import tech.thdev.composable.architecture.action.system.base.ActionViewModel
import tech.thdev.composable.architecture.router.system.Navigator
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter
import tech.thdev.composable.architecture.sample.feature.detail.api.model.DetailData
import tech.thdev.composable.architecture.sample.feature.detail.model.DetailUiState
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    flowActionStream: FlowActionStream,
    private val savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
) : ActionViewModel<DetailAction>(flowActionStream, DetailAction::class) {

    private val _detailUiState = MutableStateFlow(DetailUiState.Default)
    val detailUiState = _detailUiState.asStateFlow()

    override fun onCreated() {
        nextAction(DetailAction.Task)
    }

    override suspend fun handleAction(action: DetailAction) {
        when (action) {
            is DetailAction.Task -> {
                _detailUiState.value = DetailUiState(
                    message = savedStateHandle.get<DetailData>(DetailActivityRouter.PUT_DATA)?.text ?: "",
                )
            }

            is DetailAction.MoveBack -> {
                navigator.navigateBack()
            }
        }
    }
}
