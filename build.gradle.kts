import dev.deftu.gradle.utils.GameSide

plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.bloom")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.minecraft.loom")
}

toolkitMultiversion {
    moveBuildsToRootProject.set(true)
}

toolkitLoomHelper {
    disableRunConfigs(GameSide.SERVER)
}

repositories {
    maven("https://maven.terraformersmc.com/releases")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.isxander.dev/releases")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    modImplementation("net.fabricmc.fabric-api:fabric-api:${mcData.dependencies.fabric.fabricApiVersion}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${mcData.dependencies.fabric.fabricLanguageKotlinVersion}") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "net.fabricmc")
    }

    modApi("me.shedaniel.cloth:cloth-config-fabric:8.2.88") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "net.fabricmc")
    }
    modImplementation(mcData.dependencies.fabric.modMenuDependency) {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "net.fabricmc")
    }
}

tasks.remapJar {
    val dest = rootProject.layout.buildDirectory.asFile.get().resolve("versions")
    if (!dest.exists()) {
        dest.mkdirs()
    }

    destinationDirectory.set(dest)
}
