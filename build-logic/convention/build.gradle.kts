import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.zerosom.android.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("hilt") {
            id = "zerosome.hilt"
            implementationClass = "HiltConventionPlugin"
        }

        register("application") {
            id = "zerosome.application"
            implementationClass = "AndroidApplicationPlugin"
        }

        register("library") {
            id = "zerosome.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("design") {
            id = "zerosome.design"
            implementationClass = "DesignPlugin"
        }

        register("domain") {
            id = "zerosome.domain"
            implementationClass = "DomainPlugin"
        }

        register("domain-specified") {
            id = "zerosome.domain.specification"
            implementationClass = "DomainSpecifiedPlugin"
        }

        register("feature") {
            id = "zerosome.feature"
            implementationClass = "FeaturePlugin"
        }
    }
}
