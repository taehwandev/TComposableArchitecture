import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryFeatureComposeApiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("tech.thdev.android.library")
                apply("tech.thdev.kotlin.library.serialization")
                apply("kotlin-parcelize")
            }

            dependencies {
                implementation(project(":core:ui:composable-architecture-router-system"))
            }
        }
    }
}
