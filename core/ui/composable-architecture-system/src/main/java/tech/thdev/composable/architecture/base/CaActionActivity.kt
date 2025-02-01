package tech.thdev.composable.architecture.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import tech.thdev.composable.architecture.action.system.CaActionSender
import tech.thdev.composable.architecture.action.system.compose.LocalCaActionSenderOwner
import javax.inject.Inject

/**
 * Implement [CaActionActivity] by inheriting from it to utilize [CaActionSender].
 *
 * ```kotlin
 * @AndroidEntryPoint
 * class MainActivity : CaActionActivity() {
 *
 *      @Composable
 *      fun ContentView() {
 *          SomeTheme {
 *              // Your code
 *          }
 *      }
 * }
 * ```
 */
abstract class CaActionActivity : ComponentActivity() {

    @Inject
    lateinit var caActionSender: CaActionSender

    @Composable
    abstract fun ContentView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(
                LocalCaActionSenderOwner provides caActionSender,
            ) {
                ContentView()
            }
        }
    }
}
