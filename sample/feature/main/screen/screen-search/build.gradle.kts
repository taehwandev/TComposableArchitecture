plugins {
    alias(libs.plugins.tech.thdev.android.library.feature.compose)
    alias(libs.plugins.tech.thdev.android.library.navigation)
}

setNamespace("sample.feature.main.screen.search")

dependencies {
    implementation(projects.sample.feature.main.screen.screenSearchApi)
    implementation(projects.sample.feature.detail.detailApi)
}
