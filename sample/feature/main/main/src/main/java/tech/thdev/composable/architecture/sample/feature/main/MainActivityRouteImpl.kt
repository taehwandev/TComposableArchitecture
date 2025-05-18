package tech.thdev.composable.architecture.sample.feature.main

import android.content.Context
import android.content.Intent
import tech.thdev.composable.architecture.sample.feature.main.api.MainActivityRouter
import javax.inject.Inject

internal class MainActivityRouteImpl @Inject constructor() : MainActivityRouter {

    override fun getActivity(context: Context): Intent =
        Intent(context, MainActivity::class.java)
}
