import dev.deftu.gradle.utils.GameSide
import dev.deftu.gradle.utils.MinecraftVersion

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

    modApi("me.shedaniel.cloth:cloth-config-fabric:${when (mcData.version) {
        MinecraftVersion.VERSION_1_18_2 -> "6.5.102"
        MinecraftVersion.VERSION_1_19_2 -> "8.3.134"
        MinecraftVersion.VERSION_1_20_1 -> "11.1.136"
        MinecraftVersion.VERSION_1_20_2 -> "12.0.137"
        MinecraftVersion.VERSION_1_20_3 -> "13.0.138"
//        MinecraftVersion.VERSION_1_20_4 -> "13.0.138"
//        MinecraftVersion.VERSION_1_20_6 -> "14.0.139"
//        MinecraftVersion.VERSION_1_21 -> "15.0.140"
//        MinecraftVersion.VERSION_1_21_1 -> "15.0.140"
        else -> "13.0.138"
    }}") {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "net.fabricmc")
    }

    modImplementation(mcData.dependencies.fabric.modMenuDependency) {
        exclude(group = "net.fabricmc.fabric-api")
        exclude(group = "net.fabricmc")
    }
}
