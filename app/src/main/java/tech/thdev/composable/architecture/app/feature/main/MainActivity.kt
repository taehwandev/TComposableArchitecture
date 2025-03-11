package tech.thdev.composable.architecture.app.feature.main

import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.composable.architecture.alert.system.CaAlertScreen
import tech.thdev.composable.architecture.app.feature.main.compose.MainScreen
import tech.thdev.composable.architecture.app.feature.main.ui.theme.TComposableArchitectureTheme
import tech.thdev.composable.architecture.base.CaActionActivity
import tech.thdev.composable.architecture.lifecycle.LaunchedLifecycleViewModel
import tech.thdev.composable.architecture.lifecycle.collectLifecycleEvent

@AndroidEntryPoint
class MainActivity : CaActionActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @Composable
    override fun ContentView() {
        TComposableArchitectureTheme {
            val snackbarHostState = remember { SnackbarHostState() }

            CaAlertScreen(
                snackbarHostState = snackbarHostState,
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Composable Architecture example")
                        }
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                modifier = Modifier
                    .fillMaxSize()
            ) { innerPadding ->
                MainScreen(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 10.dp)
                )
            }

            LaunchedLifecycleViewModel(
                viewModel = mainViewModel,
            )

            mainViewModel.sideEffect.collectLifecycleEvent {
                when (it) {
                    SideEffect.ShowToast -> {
                        Toast.makeText(this@MainActivity, "message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
