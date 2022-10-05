import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import xyz.unifycraft.gradle.utils.GameSide

plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("xyz.unifycraft.gradle.multiversion")
    id("xyz.unifycraft.gradle.tools")
    id("xyz.unifycraft.gradle.tools.loom")
    id("xyz.unifycraft.gradle.tools.blossom")
}

loomHelper {
    disableRunConfigs(GameSide.SERVER)
}

repositories {
    removeIf {
        it is MavenArtifactRepository && it.url.toString().contains("unifycraft")
    }
    maven("https://maven.terraformersmc.com/releases")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.isxander.dev/releases")
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    modImplementation("net.fabricmc.fabric-api:fabric-api:${when (mcData.version) {
        11902 -> "0.62.0+1.19.2"
        11802 -> "0.57.0+1.18.2"
        else -> throw IllegalStateException("Invalid MC version: ${mcData.version}")
    }}")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.8.2+kotlin.1.7.10")

    modApi("me.shedaniel.cloth:cloth-config-fabric:8.2.88") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    modImplementation("com.terraformersmc:modmenu:${when (mcData.version) {
        11902 -> "4.0.6"
        11802 -> "3.2.3"
        else -> throw IllegalStateException("Invalid MC version: ${mcData.version}")
    }}")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjvm-default=enable"
        }
    }

    remapJar {
        archiveBaseName.set("${modData.name}-${mcData.versionStr}")
    }
}
