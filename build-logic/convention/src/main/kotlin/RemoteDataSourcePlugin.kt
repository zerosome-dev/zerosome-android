import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RemoteDataSourcePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("zerosome.library")
                apply("zerosome.hilt")
            }

            dependencies {
                add("implementation", project(":network"))

                add("implementation", project(":datasource:local")) // 삭제 필요 요망
            }
        }
    }
}