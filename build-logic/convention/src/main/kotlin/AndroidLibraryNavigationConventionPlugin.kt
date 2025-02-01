import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import tech.thdev.gradle.configureDaggerHilt
import tech.thdev.gradle.extensions.findLibrary

class AndroidLibraryNavigationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(findLibrary("androidx-compose-navigation"))
                implementation(findLibrary("androidx-compose-navigation-hilt"))
            }
        }
    }
}
