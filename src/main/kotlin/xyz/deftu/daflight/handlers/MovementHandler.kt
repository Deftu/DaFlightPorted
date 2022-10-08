package xyz.deftu.daflight.handlers

import xyz.deftu.daflight.DaFlight
import xyz.deftu.daflight.utils.FlightMode
import xyz.deftu.daflight.utils.Rotation
import xyz.deftu.daflight.utils.Vector3D
import kotlin.math.*

object MovementHandler {
    private var moveForward = 0f
    private var moveStrafe = 0f

    var maxFlySpeed = 10f
    var maxWalkSpeed = 10f
    private var flying = false
    private var sprinting = false
    private var flyBoosting = false
    private var sprintBoosting = false

    private var ascending = false
    private var descending = false

    @JvmStatic
    fun reset() {
        flying = false
        sprinting = false
        flyBoosting = false
        sprintBoosting = false
        maxFlySpeed = if (DaFlight.singleplayerState) 100000f else 1f
        maxWalkSpeed = if (DaFlight.singleplayerState) 100000f else 1f
    }

    @JvmStatic
    fun applyMovement(vec: Vector3D, rotation: Rotation) {
        if (flying && DaFlight.config.toggle) {
            moveFlying(vec, rotation)
        } else if (sprinting && DaFlight.config.toggle) {
            moveSprinting(vec, rotation)
        }
    }

    @JvmStatic
    fun setMovement(moveForward: Float, moveStrafe: Float) {
        this.moveForward = moveForward
        this.moveStrafe = moveStrafe
    }

    @JvmStatic
    fun jump(original: Float): Float {
        if (!sprinting || !DaFlight.config.toggle)
            return original

        val boost = if (sprintBoosting) DaFlight.config.sprintBoost else 1f
        val speed = min(DaFlight.config.sprintSpeed * DaFlight.config.jumpModifier * boost, maxWalkSpeed * 5f)
        return original * speed
    }

    fun isFlying() = flying
    fun setFlying(state: Boolean) {
        flying = state
    }

    fun isFlyBoosting() = flyBoosting
    fun setFlyBoosting(state: Boolean) {
        flyBoosting = state
    }

    fun isSprinting() = sprinting
    fun setSprinting(state: Boolean) {
        sprinting = state
    }

    fun isSprintBoosting() = sprintBoosting
    fun setSprintBoosting(state: Boolean) {
        sprintBoosting = state
    }

    fun setAscending(state: Boolean) {
        ascending = state
    }

    fun setDescending(state: Boolean) {
        descending = state
    }

    @JvmStatic
    fun shouldDisableFov() = DaFlight.config.disableFov && flying
    @JvmStatic
    fun shouldDisableViewBobbing() = flying

    private fun moveFlying(vec: Vector3D, rotation: Rotation) {
        val strafeMod = DaFlight.config.strafeModifier
        val ascendMod = DaFlight.config.verticalModifier
        val boost = if (flyBoosting) DaFlight.config.flyBoost else 1f
        val speed = (DaFlight.config.flySpeed * boost).coerceAtMost(maxFlySpeed)

        val pitch = Math.toRadians(rotation.pitch.toDouble())
        val yaw = Math.toRadians(rotation.yaw.toDouble())
        val dx = -sin(yaw)
        val dz = cos(yaw)

        vec.set(dx * moveForward, 0.0, dz * moveForward)
        vec.add(dz * moveStrafe * strafeMod, 0.0, -dx * moveStrafe * strafeMod)

        if (!DaFlight.isGameInactive()) {
            if (ascending)
                vec.add(0.0, ascendMod.toDouble(), 0.0)
            if (descending)
                vec.add(0.0, -ascendMod.toDouble(), 0.0)
        }

        if (DaFlight.config.flyMode == FlightMode.THREE_DIMENSIONAL) {
            val vy = -sin(pitch)
            val hy = abs(cos(pitch))
            vec.mult(hy, 1.0, hy)
            vec.add(0.0, vy * moveForward * ascendMod, 0.0)
        }

        vec.norm()
        vec.mult(speed.toDouble())
    }

    private fun moveSprinting(vec: Vector3D, rotation: Rotation) {
        val strafeMod = DaFlight.config.strafeModifier
        val boost = if (sprintBoosting) DaFlight.config.sprintBoost else 1f
        val speed = (DaFlight.config.sprintSpeed * boost).coerceAtMost(maxWalkSpeed)

        val rads = Math.toRadians(rotation.yaw.toDouble())
        val dx = -sin(rads)
        val dz = cos(rads)

        vec.add(dx * moveForward, 0.0, dz * moveForward)
        vec.add(dz * moveStrafe * strafeMod, 0.0, -dx * moveStrafe * strafeMod)
        vec.mult(speed.toDouble(), 1.0, speed.toDouble())
    }
}
