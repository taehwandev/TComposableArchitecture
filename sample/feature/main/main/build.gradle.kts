plugins {
    alias(libs.plugins.tech.thdev.android.library.feature.compose)
}

setNamespace("sample.feature.main")

dependencies {
    implementation(projects.sample.feature.main.mainApi)

    implementation(projects.sample.feature.main.screen.screenNavigation)
    implementation(projects.sample.feature.main.screen.screenSearch)
    implementation(projects.sample.feature.main.screen.screenSettings)
}
