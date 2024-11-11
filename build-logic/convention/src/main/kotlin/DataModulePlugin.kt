import com.zerosome.android.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DataModulePlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("zerosome.library")
                apply("zerosome.hilt")
            }

            dependencies {
                add("implementation", project(":network"))
                add("implementation", project(":localdb"))
                add("implementation", project(":datasource:local"))
                add("implementation", project(":datasource:remote"))
                add("implementation", project(":domain"))

                add("implementation", libs.findLibrary("kotlin-serialization").get())
            }
        }
    }
}