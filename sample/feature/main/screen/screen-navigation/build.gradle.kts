plugins {
    alias(libs.plugins.tech.thdev.android.library.feature.compose)
}

setNamespace("sample.feature.main.screen.navigation")

dependencies {
    implementation(projects.sample.feature.main.screen.screenSearch)
    implementation(projects.sample.feature.main.screen.screenSearchApi)
    implementation(projects.sample.feature.main.screen.screenSettings)
    implementation(projects.sample.feature.main.screen.screenSettingsApi)
}
