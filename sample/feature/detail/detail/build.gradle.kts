plugins {
    alias(libs.plugins.tech.thdev.android.library.feature.compose)
}

setNamespace("sample.feature.detail")

dependencies {
    implementation(projects.sample.feature.detail.detailApi)
}
