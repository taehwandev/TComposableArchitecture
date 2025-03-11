package tech.thdev.composable.architecture.sample.feature.detail.api

import tech.thdev.composable.architecture.router.system.navigation.CaActivityRoute

interface DetailActivityRouter : CaActivityRoute {

    companion object {

        const val PUT_DATA = "put-data"
    }
}
