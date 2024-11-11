import com.zerosome.android.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DesignPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("zerosome.library")

            dependencies {
                add("implementation", libs.findLibrary("coil-compose"))

                add("implementation", libs.findLibrary("androidx-ui"))
                add("implementation", libs.findLibrary("androidx-ui-graphics"))
                add("implementation", libs.findLibrary("androidx-ui-tooling-preview"))
                add("implementation", libs.findLibrary("androidx-material3"))
            }
        }
    }
}