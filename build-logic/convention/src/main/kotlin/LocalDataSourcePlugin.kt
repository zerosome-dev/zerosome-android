import com.zerosome.android.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class LocalDataSourcePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("zerosome.library")
                apply("zerosome.hilt")
                apply(libs.findPlugin("kotlinSerialization"))
            }

            dependencies {
                add("implementation", project(":localdb"))

                add("implementation", libs.findLibrary("kotlin-serialization"))
            }
        }
    }
}