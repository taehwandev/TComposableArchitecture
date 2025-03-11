import tech.thdev.gradle.configureComposeFeature

plugins {
    alias(libs.plugins.tech.thdev.android.library)
}

setNamespace("sample.resource")

configureComposeFeature()

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
}
