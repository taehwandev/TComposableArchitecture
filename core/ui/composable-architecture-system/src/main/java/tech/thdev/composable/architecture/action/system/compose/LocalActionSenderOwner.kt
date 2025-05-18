package tech.thdev.composable.architecture.action.system.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import tech.thdev.composable.architecture.action.system.ActionSender
import tech.thdev.composable.architecture.action.system.base.ActionViewModel

/**
 * Define [tech.thdev.composable.architecture.action.system.ActionSender] at the beginning of the Compose hierarchy.
 * Since Hilt Singleton is used, it's injected as follows:
 *
 * Apply action - your compose screen
 * ```kotlin
 * @Composable
 * fun SomeScreen(
 *      someViewModel: SomeViewModel = actionViewModelActivate(), // or, Use navigation actionHiltViewModelActivate()
 * ) {
 *     ActionSenderCompositionLocalProvider(someViewModel) {
 *          val uiState by someViewModel.uiState.collectAsStateWithLifecycle()
 *          SomeScreen(
 *              uiState = uiState,
 *          )
 *     }
 * }
 * ```
 *
 * Action Define
 * ```kotlin
 * sealed interface SomeAction : Action {
 *      data object Move : SomeAction
 * }
 * ```
 *
 * Use LocalActionSenderOwner
 *
 * ```kotlin
 * @Composable
 * fun SomeScreen(uiState: UiState) {
 *      val actionSender = LocalActionSenderOwner.current
 *
 *      Column {
 *          Text(
 *              text = uiState.text
 *          )
 *
 *          Button(
 *              onClick = actionSender.send(Action.Move),
 *          ) {
 *              Text(text = "Move")
 *          }
 *      }
 * }
 * ```
 */
object LocalActionSenderOwner {

    private val LocalComposition = staticCompositionLocalOf<ActionSender?> { null }

    val current: ActionSender?
        @Composable
        get() = LocalComposition.current

    internal infix fun provides(registerOwner: ActionSender): ProvidedValue<ActionSender?> =
        LocalComposition provides registerOwner
}

@Composable
fun ActionSenderCompositionLocalProvider(
    actionViewModel: ActionViewModel<*>,
    body: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalActionSenderOwner provides actionViewModel.actionSender,
    ) {
        body()
    }
}
