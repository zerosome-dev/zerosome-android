import com.zerosome.android.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("zerosome.hilt")
                apply("zerosome.android.compose")
            }


            dependencies {
                add("implementation", project(":feat:core"))
                add("implementation", project(":design"))
                add("implementation", project(":domain"))

                add("implementation", libs.findLibrary("navigation-compose").get())
                add("implementation", libs.findLibrary("hilt-compose").get())
            }
        }
    }
}