import tech.thdev.gradle.configureComposeFeature

plugins {
    alias(libs.plugins.tech.thdev.android.library)
    alias(libs.plugins.tech.thdev.android.library.hilt)
    alias(libs.plugins.tech.thdev.android.library.publish)
}

setNamespace("alert.system")

val (majorVersion, minorVersion, patchVersion, code) = getVersionInfo()

ext["libraryName"] = "composable-architecture-alert-system"
ext["libraryVersion"] = "$majorVersion.$minorVersion.$patchVersion"
ext["description"] = Publish.DESCRIPTION
ext["url"] = Publish.PUBLISH_URL

configureComposeFeature()

android {
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // AGP 8.0
    publishing {
        multipleVariants("release") {
            allVariants()
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.compose.lifecycle.viewModel)

    implementation(projects.core.ui.composableArchitectureSystem)
}
