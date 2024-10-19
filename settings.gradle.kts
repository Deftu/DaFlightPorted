import groovy.lang.MissingPropertyException

pluginManagement {
    repositories {
        // Default repositories
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()

        // Repositories
        maven("https://maven.deftu.dev/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://server.bbkr.space/artifactory/libs-release/")
        maven("https://maven.jab125.dev/")
        maven("https://jitpack.io/")

        // Snapshots
        maven("https://maven.deftu.dev/snapshots")
        maven("https://s01.oss.sonatype.org/content/groups/public/")
    }

    plugins {
        val kotlin = "2.0.20"
        kotlin("jvm") version(kotlin)
        kotlin("plugin.serialization") version(kotlin)

        val dgt = "2.11.2"
        id("dev.deftu.gradle.multiversion-root") version(dgt)
    }
}

val projectName: String = extra["mod.name"]?.toString()
    ?: throw MissingPropertyException("mod.name has not been set.")
rootProject.name = projectName
rootProject.buildFileName = "root.gradle.kts"

listOf(
    "1.18.2-fabric",
    "1.19.2-fabric",
    "1.20.1-fabric",
    "1.20.2-fabric",
    "1.20.3-fabric",
    "1.20.4-fabric"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../build.gradle.kts"
    }
}