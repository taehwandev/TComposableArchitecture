import org.gradle.api.Plugin
import org.gradle.api.Project
import tech.thdev.gradle.configureComposeAndroid
import tech.thdev.gradle.configureComposeFeature
import tech.thdev.gradle.configureCoroutineAndroid

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("tech.thdev.android.library")
                apply("tech.thdev.android.library.compose")
                apply("tech.thdev.android.library.unit.test")
            }

            configureComposeFeature()
            configureComposeAndroid()
            configureCoroutineAndroid()
        }
    }
}
