package tech.thdev.composable.architecture.router.system

import android.content.Context
import android.content.Intent
import org.mockito.kotlin.mock
import tech.thdev.composable.architecture.router.system.navigation.ActivityRoute

internal class MockActivityRoute : ActivityRoute {

    override fun getActivity(context: Context): Intent =
        mock()
}
