package tech.thdev.composable.architecture.sample.feature.main.screen.main.compose

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tech.thdev.composable.architecture.action.system.compose.ActionSenderCompositionLocalProvider
import tech.thdev.composable.architecture.action.system.compose.LocalActionSenderOwner
import tech.thdev.composable.architecture.action.system.lifecycle.collectLifecycleEvent
import tech.thdev.composable.architecture.action.system.send
import tech.thdev.composable.architecture.sample.feature.main.screen.main.MainAction
import tech.thdev.composable.architecture.sample.feature.main.screen.main.MainSideEffect
import tech.thdev.composable.architecture.sample.feature.main.screen.main.MainViewModel
import tech.thdev.composable.architecture.sample.resource.R

@Composable
internal fun InternalMainScaffold(
    mainViewModel: MainViewModel = viewModel(),
) {
    ActionSenderCompositionLocalProvider(mainViewModel) {
        val activity = LocalActivity.current
        mainViewModel.sideEffect.collectLifecycleEvent {
            when (it) {
                MainSideEffect.ShowToast -> {
                    Toast.makeText(activity, "message", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Composable Architecture example")
                    }
                )
            },
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            InternalMainScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

@Composable
private fun InternalMainScreen(
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
private fun PreviewInternalMainScreen() {
    InternalMainScreen(
        modifier = Modifier
            .fillMaxSize()
    )
}
