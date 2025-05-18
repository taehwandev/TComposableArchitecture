package tech.thdev.composable.architecture.action.system

/**
 * Define an Action by inheriting and implementing [Action].
 *
 * ```kotlin
 * sealed interface Action : CaAction {
 *
 *      data object SomeAction : Action
 *
 *      data object Move : Action
 * }
 * ```
 */
interface Action
