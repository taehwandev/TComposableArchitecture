package tech.thdev.gradle

import kspSourceSet
import org.gradle.api.Project
import tech.thdev.gradle.extensions.androidExtension

/**
 * KSP Source sets
 * 모듈 용
 */
internal fun Project.configureKspSourceSets() {
    androidExtension.apply {
        sourceSets.getByName("debug") {
            java.srcDir("debug".kspSourceSet)
        }
        sourceSets.getByName("release") {
            java.srcDir("release".kspSourceSet)
        }
    }
}
