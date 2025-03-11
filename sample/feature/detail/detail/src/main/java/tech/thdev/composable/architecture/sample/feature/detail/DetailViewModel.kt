package tech.thdev.composable.architecture.sample.feature.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.thdev.composable.architecture.action.system.FlowCaActionStream
import tech.thdev.composable.architecture.base.CaViewModel
import tech.thdev.composable.architecture.router.system.CaRouterAction
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter
import tech.thdev.composable.architecture.sample.feature.detail.api.model.DetailData
import tech.thdev.composable.architecture.sample.feature.detail.model.DetailUiState
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    flowCaActionStream: FlowCaActionStream,
    private val savedStateHandle: SavedStateHandle,
) : CaViewModel<Action>(flowCaActionStream, Action::class) {

    private val _detailUiState = MutableStateFlow(DetailUiState.Default)
    val detailUiState = _detailUiState.asStateFlow()

    override fun onLoad() {
        super.onLoad()
        nextAction(Action.Task)
    }

    override suspend fun reducer(action: Action) {
        when (action) {
            is Action.Task -> {
                _detailUiState.value = DetailUiState(
                    message = savedStateHandle.get<DetailData>(DetailActivityRouter.PUT_DATA)?.text ?: "",
                )
            }

            is Action.MoveBack -> {
                nextAction(CaRouterAction.MoveActivityBack)
            }
        }
    }
}
