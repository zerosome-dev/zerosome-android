import org.gradle.api.Plugin
import org.gradle.api.Project

class DomainSpecifiedPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("zerosome.library")
                apply("zerosome.hilt")
            }

            extensions.add("api", project(":domain"))
        }
    }
}