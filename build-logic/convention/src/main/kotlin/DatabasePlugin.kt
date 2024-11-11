import com.zerosome.android.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DatabasePlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("zerosome.library")
            }

            dependencies {
                add("api", libs.findLibrary("datastore-preference").get())
            }
        }
    }
}