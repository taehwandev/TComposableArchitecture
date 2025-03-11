plugins {
    alias(libs.plugins.tech.thdev.android.application)
    alias(libs.plugins.tech.thdev.android.library.hilt)
}

setNamespace("app")

android {
    val (majorVersion, minorVersion, patchVersion, code) = getVersionInfo()

    defaultConfig {
        applicationId = "tech.thdev.imageex"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        vectorDrawables.useSupportLibrary = true
        versionCode = code
        versionName = "$majorVersion.$minorVersion.$patchVersion"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
}

ksp {
    arg("moduleName", project.name)
    arg("rootDir", rootDir.absolutePath)
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.core)

    implementation(libs.androidx.compose.activity)

    projects.core.filterImplementation {
        implementation(it)
    }

    projects.sample.filterImplementation {
        implementation(it)
    }
}
