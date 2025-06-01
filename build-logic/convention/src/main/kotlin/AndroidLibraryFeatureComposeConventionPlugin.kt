import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryFeatureComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("tech.thdev.android.library.compose")
                apply("tech.thdev.android.library.hilt")
                apply("tech.thdev.android.library.navigation")
            }

            dependencies {
                implementation(project(":core:ui:composable-architecture-system"))
                implementation(project(":core:ui:composable-architecture-alert-system"))
                implementation(project(":core:ui:composable-architecture-router-system"))

                implementation(project(":sample:core:ui:resource"))
            }
        }
    }
}
