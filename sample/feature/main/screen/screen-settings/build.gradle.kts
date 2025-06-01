plugins {
    alias(libs.plugins.tech.thdev.android.library.feature.compose)
}

setNamespace("sample.feature.main.screen.settings")

dependencies {
    implementation(projects.sample.feature.main.screen.screenSettingsApi)
}