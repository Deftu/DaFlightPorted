package xyz.deftu.daflight.handlers

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.option.StickyKeyBinding
import net.minecraft.client.util.InputUtil
import xyz.deftu.daflight.DaFlight
import xyz.deftu.daflight.utils.ToggleKeyBinding

object InputHandler {
    @JvmStatic
    lateinit var flyKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var sprintKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var boostKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var flyUpKeyBind: KeyBinding
        private set
    @JvmStatic
    lateinit var flyDownKeyBind: KeyBinding
        private set

    fun initialize() {
        flyKeyBind = setupKeybind("Fly", InputUtil.GLFW_KEY_G, true)
        sprintKeyBind = setupKeybind("Sprint", InputUtil.GLFW_KEY_R, true)
        boostKeyBind = setupKeybind("Fly Boost", InputUtil.GLFW_KEY_R, true)
        flyUpKeyBind = setupKeybind("Fly Up", InputUtil.GLFW_KEY_UP)
        flyDownKeyBind = setupKeybind("Fly Down", InputUtil.GLFW_KEY_DOWN)

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if (client.world == null || DaFlight.isGameInactive())
                return@register

            handle()
        }
    }

    private fun handle() {
        val config = DaFlight.config
        if (!config.toggle)
            return

        val wasFlying = MovementHandler.isFlying()
        val wasSprintng = MovementHandler.isSprinting()

        if (config.input.flyKeyBind) {
            MovementHandler.setFlying(if (flyKeyBind.isTogglePressed) !MovementHandler.isFlying() && DaFlight.isFlyingAllowed() else MovementHandler.isFlying())
        } else MovementHandler.setFlying(flyKeyBind.isPressed && DaFlight.isFlyingAllowed())

        if (config.input.flyUpKeyBind)
            MovementHandler.setAscending(flyUpKeyBind.isPressed)
        if (config.input.flyDownKeyBind)
            MovementHandler.setDescending(flyDownKeyBind.isPressed)

        if (config.input.sprintKeyBind) {
            MovementHandler.setSprinting(if (sprintKeyBind.isTogglePressed) !MovementHandler.isSprinting() && DaFlight.isFlyingAllowed() else MovementHandler.isSprinting())
        } else MovementHandler.setSprinting(sprintKeyBind.isPressed && DaFlight.isFlyingAllowed())

        if (config.input.boostKeyBind) {
            if (boostKeyBind.isPressed) {
                MovementHandler.setFlyBoosting(MovementHandler.isFlying() != MovementHandler.isFlyBoosting())
                MovementHandler.setSprintBoosting((!MovementHandler.isFlying() && MovementHandler.isSprinting()) != MovementHandler.isSprintBoosting())
            }
        } else {
            MovementHandler.setFlyBoosting(if (MovementHandler.isFlying()) boostKeyBind.isPressed else MovementHandler.isFlyBoosting())
            MovementHandler.setSprintBoosting(if (!MovementHandler.isFlying() && MovementHandler.isSprinting()) boostKeyBind.isPressed else MovementHandler.isSprintBoosting())
        }

        if (!DaFlight.isFlyingAllowed()) {
            val updated = MovementHandler.isFlying() || MovementHandler.isSprinting()
            MovementHandler.setFlying(false)
            MovementHandler.setSprinting(false)
            MovementHandler.setFlyBoosting(false)
            MovementHandler.setSprintBoosting(false)
            if (updated) {
                DaFlight.player?.sendAbilitiesUpdate()
            }
        }

        if (wasFlying != MovementHandler.isFlying()) {
            DaFlight.player?.abilities?.flying = MovementHandler.isFlying()
            DaFlight.setPlayerInvincible(MovementHandler.isFlying() || MovementHandler.isSprinting())
            DaFlight.player?.sendAbilitiesUpdate()
        }

        if (wasSprintng != MovementHandler.isSprinting()) {
            DaFlight.setPlayerInvincible(MovementHandler.isFlying() || MovementHandler.isSprinting())
        }
    }

    private val KeyBinding.isTogglePressed: Boolean
        get() = (this as ToggleKeyBinding).isToggled()
    private fun setupKeybind(name: String, key: Int, toggleable: Boolean = false): KeyBinding =
        (if (toggleable) ToggleKeyBinding(name, DaFlight.NAME, key) else KeyBinding(name, key, DaFlight.NAME)).also(KeyBindingHelper::registerKeyBinding)
}
