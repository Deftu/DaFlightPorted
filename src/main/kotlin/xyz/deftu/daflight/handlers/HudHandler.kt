package xyz.deftu.daflight.handlers

import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.util.math.MatrixStack
import xyz.deftu.daflight.DaFlight

object HudHandler {
    private val textRenderer: TextRenderer
        get() = MinecraftClient.getInstance().textRenderer

    @JvmStatic
    fun render(stack: MatrixStack) {
        if (DaFlight.config.hud && DaFlight.isGameFocused() && !MinecraftClient.getInstance().options.debugEnabled) {
            val x = 5f
            var y = 5f

            if (MovementHandler.isFlying()) {
                textRenderer.draw(stack, "flying" + if (MovementHandler.isFlyBoosting()) "+" else "", x, y, 0xFFFFFF)
                y += 9
            }

            if (MovementHandler.isSprinting()) {
                textRenderer.draw(stack, "sprinting" + if (MovementHandler.isSprintBoosting()) "+" else "", x, y, 0xFFFFFF)
            }
        }
    }
}
