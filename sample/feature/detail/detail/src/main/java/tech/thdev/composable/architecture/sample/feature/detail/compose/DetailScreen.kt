package tech.thdev.composable.architecture.sample.feature.detail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.thdev.composable.architecture.sample.feature.detail.DetailViewModel
import tech.thdev.composable.architecture.sample.feature.detail.model.DetailUiState

@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    val detailUiState by detailViewModel.detailUiState.collectAsStateWithLifecycle()

    DetailScreen(
        detailUiState = detailUiState,
        modifier = modifier,
    )
}

@Composable
private fun DetailScreen(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = detailUiState.message,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen() {
    DetailScreen(
        detailUiState = DetailUiState(
            message = "next message",
        ),
        modifier = Modifier
            .fillMaxSize()
    )
}
