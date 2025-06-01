package tech.thdev.composable.architecture.sample.feature.main.screen.search.compose

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.thdev.composable.architecture.action.system.compose.ActionSenderCompositionLocalProvider
import tech.thdev.composable.architecture.action.system.compose.LocalActionSenderOwner
import tech.thdev.composable.architecture.action.system.lifecycle.collectLifecycleEvent
import tech.thdev.composable.architecture.action.system.send
import tech.thdev.composable.architecture.sample.feature.main.screen.search.MainAction
import tech.thdev.composable.architecture.sample.feature.main.screen.search.SearchSideEffect
import tech.thdev.composable.architecture.sample.feature.main.screen.search.SearchViewModel
import tech.thdev.composable.architecture.sample.resource.R

@Composable
internal fun InternalSearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    ActionSenderCompositionLocalProvider(searchViewModel) {
        val activity = LocalActivity.current
        searchViewModel.sideEffect.collectLifecycleEvent {
            when (it) {
                SearchSideEffect.ShowToast -> {
                    Toast.makeText(activity, "message", Toast.LENGTH_SHORT).show()
                }
            }
        }

        InternalSearchScreen(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        )
    }
}

@Composable
private fun InternalSearchScreen(
    modifier: Modifier = Modifier,
) {
    val actionSender = LocalActionSenderOwner.current

    Column(
        modifier = modifier
    ) {
        Button(
            onClick = actionSender.send(MainAction.ShowToast),
        ) {
            Text(
                text = "ShowToast",
            )
        }

        Button(
            onClick = actionSender.send(
                MainAction.ShowAlert(
                    icon = R.drawable.baseline_info_24,
                    title = "Info",
                    message = "A dialog is a type of modal window that appears in front of app content to provide critical" +
                            "information, or ask for a decision to be made.",
                    confirmButtonText = "Confirm",
                    dismissButtonText = "Dismiss",
                )
            ),
        ) {
            Text(
                text = "ShowAlert",
            )
        }

        Button(
            onClick = actionSender.send(MainAction.ShowDetail(message = "Show detail activity")),
        ) {
            Text(
                text = "Show Detail activity",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInternalSearchScreen() {
    InternalSearchScreen(
        modifier = Modifier
            .fillMaxSize()
    )
}
