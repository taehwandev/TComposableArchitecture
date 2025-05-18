package tech.thdev.composable.architecture.sample.feature.detail.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class DetailUiState(
    val message: String,
) {

    companion object {

        val Default = DetailUiState(
            message = "",
        )
    }
}
