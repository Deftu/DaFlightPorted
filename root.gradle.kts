plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    val fabric_1_21_01 = createNode("1.21.1-fabric", 1_21_01, "yarn")
    val fabric_1_21 = createNode("1.21-fabric", 1_21_00, "yarn")
    val fabric_1_20_06 = createNode("1.20.6-fabric", 1_20_06, "yarn")
    val fabric_1_20_04 = createNode("1.20.4-fabric", 1_20_04, "yarn")
    val fabric_1_20_03 = createNode("1.20.3-fabric", 1_20_03, "yarn")
    val fabric_1_20_02 = createNode("1.20.2-fabric", 1_20_02, "yarn")
    val fabric_1_20_01 = createNode("1.20.1-fabric", 1_20_01, "yarn")
    val fabric_1_19_02 = createNode("1.19.2-fabric", 1_19_02, "yarn")
    val fabric_1_18_02 = createNode("1.18.2-fabric", 1_18_02, "yarn")

    fabric_1_21_01.link(fabric_1_21)
    fabric_1_21.link(fabric_1_20_06)
    fabric_1_20_06.link(fabric_1_20_04)
    fabric_1_20_04.link(fabric_1_20_03)
    fabric_1_20_03.link(fabric_1_20_02)
    fabric_1_20_02.link(fabric_1_20_01)
    fabric_1_20_01.link(fabric_1_19_02)
    fabric_1_19_02.link(fabric_1_18_02)
}
