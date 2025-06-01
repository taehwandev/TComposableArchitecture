package tech.thdev.composable.architecture.sample.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.composable.architecture.router.system.LaunchedRouter
import tech.thdev.composable.architecture.sample.feature.main.screen.navigation.NavigationScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()
            LaunchedRouter(navHostController)
            NavigationScreen(navHostController)
        }
    }
}
