package tech.thdev.composable.architecture.action.system

interface ActionSender {

    fun send(action: Action)
}

fun ActionSender?.send(action: Action): () -> Unit =
    { this?.send(action) }
