package tech.thdev.composable.architecture.sample.feature.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.composable.architecture.action.system.compose.LocalActionSenderOwner
import tech.thdev.composable.architecture.action.system.lifecycle.LaunchedLifecycleActionViewModel
import tech.thdev.composable.architecture.action.system.send
import tech.thdev.composable.architecture.alert.system.CaAlertScreen
import tech.thdev.composable.architecture.router.system.LaunchedCaRouter
import tech.thdev.composable.architecture.sample.feature.detail.compose.DetailScreen
import tech.thdev.composable.architecture.sample.resource.theme.TComposableArchitectureTheme

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TComposableArchitectureTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                CaAlertScreen(
                    snackbarHostState = snackbarHostState,
                )
                LaunchedCaRouter()
                LaunchedLifecycleActionViewModel(
                    viewModel = detailViewModel,
                )

                val action = LocalActionSenderOwner.current

                Scaffold(
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(
                                    onClick = action.send(DetailAction.MoveBack),
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "back",
                                    )
                                }
                            },
                            title = {
                                Text("Detail view")
                            }
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    DetailScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}
