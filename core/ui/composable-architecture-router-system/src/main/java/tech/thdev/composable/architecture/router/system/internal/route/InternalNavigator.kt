package tech.thdev.composable.architecture.router.system.internal.route

import kotlinx.coroutines.channels.Channel

interface InternalNavigator {

    val channel: Channel<InternalRoute>
}
