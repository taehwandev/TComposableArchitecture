import tech.thdev.gradle.configureComposeFeature

plugins {
    alias(libs.plugins.tech.thdev.android.library)
    alias(libs.plugins.tech.thdev.kotlin.library.hilt)
}

setNamespace("action.system")

configureComposeFeature()

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)

    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.compose.lifecycle.viewModel)
}
