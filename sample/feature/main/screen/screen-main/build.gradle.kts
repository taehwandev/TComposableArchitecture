plugins {
    alias(libs.plugins.tech.thdev.android.library.feature)
}

setNamespace("sample.feature.main.screen.main")

dependencies {
    implementation(projects.sample.feature.detail.detailApi)
    
    implementation(libs.androidx.compose.activity)
}
