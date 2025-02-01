package tech.thdev.composable.architecture.action.system

interface CaActionSender {

    fun send(action: CaAction)
}

fun CaActionSender?.send(action: CaAction): () -> Unit =
    { this?.send(action) }
