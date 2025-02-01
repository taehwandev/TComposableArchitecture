import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import tech.thdev.gradle.extensions.findLibrary

class AndroidLibraryUnitTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(findLibrary("kotlin-stdlib"))

                implementation(findLibrary("coroutines-core"))
                testImplementation(findLibrary("test-coroutines"))
                testImplementation(findLibrary("test-coroutines-turbine"))

                testImplementation(findLibrary("test-mockito"))
                testImplementation(findLibrary("test-mockito-kotlin"))

                testImplementation(findLibrary("test-androidx-core"))
                testImplementation(findLibrary("test-androidx-runner"))
                testImplementation(findLibrary("test-androidx-junit"))
                testImplementation(findLibrary("test-robolectric")) // hilt 사용을 위해 추가
            }
        }
    }
}
