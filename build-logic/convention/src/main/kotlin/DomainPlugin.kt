import com.zerosome.android.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class DomainPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("zerosome.library")

            extensions.apply {
                add("api", project(":network")) // 차후 삭제 예정

                add("implementation", libs.findLibrary("androidx-core-ktx"))
            }
        }
    }
}