package xyz.deftu.daflight.handlers

import net.minecraft.client.MinecraftClient
import xyz.deftu.daflight.DaFlight

object HudHandler {
    @JvmStatic
    fun render() {
        if (DaFlight.config.hud && DaFlight.isGameFocused() && !MinecraftClient.getInstance().options.debugEnabled) {
            var x = 5
            var y = 5
            var active = false

            if (MovementHandler.isFlying()) {
                active = true
                // TODO
            }

            if (MovementHandler.isSprinting()) {
                active = true
                // TODO
            }
        }
    }
}
