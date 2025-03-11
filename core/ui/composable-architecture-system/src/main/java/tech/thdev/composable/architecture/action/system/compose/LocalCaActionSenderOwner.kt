package tech.thdev.composable.architecture.action.system.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import tech.thdev.composable.architecture.action.system.CaActionSender

/**
 * Define [tech.thdev.composable.architecture.action.system.CaActionSender] at the beginning of the Compose hierarchy.
 * Since Hilt Singleton is used, it's injected as follows:
 *
 * Apply action
 * ```kotlin
 * class MainActivity {
 *
 *     @Inject
 *     lateinit val caActionSender: CaActionSender
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContent {
 *             CompositionLocalProvider(LocalCaActionSenderOwner provides caActionSender) {
 *                 Theme {
 *                     // ... show view
 *                 }
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * Action Define
 * ```kotlin
 * sealed interface Action : CaAction {
 *      data object Move : Action
 * }
 * ```
 *
 * Use LocalCaActionSenderOwner
 *
 * ```kotlin
 * @Composable
 * fun SomeScreen() {
 *      val actionSender = LocalCaActionSenderOwner.current
 *
 *      Button(
 *          onClick = actionSender.send(Action.Move),
 *      ) {
 *          Text(text = "Move")
 *      }
 * }
 * ```
 */
object LocalCaActionSenderOwner {

    private val LocalComposition = staticCompositionLocalOf<CaActionSender?> { null }

    val current: CaActionSender?
        @Composable
        get() = LocalComposition.current

    infix fun provides(registerOwner: CaActionSender): ProvidedValue<CaActionSender?> =
        LocalComposition provides registerOwner
}
