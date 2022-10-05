package xyz.deftu.daflight.utils

import kotlin.math.sqrt

data class Vector3D(
    var x: Double,
    var y: Double,
    var z: Double
) {
    constructor() : this(0.0, 0.0, 0.0)

    fun length() = sqrt(x * x + y * y + z * z)

    fun set(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun add(x: Double, y: Double, z: Double) {
        this.x += x
        this.y += y
        this.z += z
    }

    fun mult(factor: Double) {
        mult(factor, factor, factor)
    }

    fun mult(x: Double, y: Double, z: Double) {
        this.x *= x
        this.y *= y
        this.z *= z
    }

    fun norm() {
        val length = length()
        if (length != 0.0) {
            x /= length
            y /= length
            z /= length
        }
    }
}
