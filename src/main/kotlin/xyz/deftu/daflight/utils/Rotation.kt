package xyz.deftu.daflight.utils

data class Rotation(
    var pitch: Float,
    var yaw: Float
) {
    constructor() : this(0f, 0f)

    fun set(pitch: Float, yaw: Float) {
        this.pitch = pitch
        this.yaw = yaw
    }
}
