plugins {
    alias(libs.plugins.tech.thdev.android.library.feature)
}

setNamespace("sample.feature.main")

dependencies {
    implementation(projects.sample.feature.main.mainApi)

    implementation(projects.sample.feature.main.screen.screenMain)
}
