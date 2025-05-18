@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Project
import tech.thdev.gradle.extensions.androidExtension

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "tech.thdev.composable.architecture.$name"
    }
}

val String.kspSourceSet: String
    get() = "build/generated/ksp/$this/kotlin"

fun Set<Project>.filterProject(
    body: (target: Project) -> Unit,
) {
    forEach { project ->
        if (project.name != "app" && project.buildFile.isFile) {
            body(project)
        }
    }
}
