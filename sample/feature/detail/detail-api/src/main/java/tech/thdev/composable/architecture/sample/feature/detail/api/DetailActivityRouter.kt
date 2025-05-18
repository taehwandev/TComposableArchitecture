package tech.thdev.composable.architecture.sample.feature.detail.api

import tech.thdev.composable.architecture.router.system.navigation.ActivityRoute

interface DetailActivityRouter : ActivityRoute {

    companion object {

        const val PUT_DATA = "put-data"
    }
}
