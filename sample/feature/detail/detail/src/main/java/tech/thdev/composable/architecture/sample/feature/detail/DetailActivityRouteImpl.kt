package tech.thdev.composable.architecture.sample.feature.detail

import android.content.Context
import android.content.Intent
import tech.thdev.composable.architecture.sample.feature.detail.api.DetailActivityRouter
import javax.inject.Inject

internal class DetailActivityRouteImpl @Inject internal constructor() : DetailActivityRouter {

    override fun getActivity(context: Context): Intent =
        Intent(context, DetailActivity::class.java)
}
