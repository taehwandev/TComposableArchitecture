package tech.thdev.composable.architecture.router.system.fake

import android.content.Context
import android.content.Intent
import org.mockito.kotlin.mock
import tech.thdev.composable.architecture.router.system.route.ActivityRoute

internal class FakeActivityRoute : ActivityRoute {

    override fun getActivity(context: Context): Intent =
        mock()
}
