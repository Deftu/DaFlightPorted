package xyz.deftu.daflight.handlers

//#if MC >= 1.20
//$$ import net.minecraft.client.gui.DrawContext
//#endif

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.util.math.MatrixStack
import xyz.deftu.daflight.DaFlight

object HudHandler {
    private val textRenderer: TextRenderer
        get() = MinecraftClient.getInstance().textRenderer

    fun initialize() {
        HudRenderCallback.EVENT.register { ctx, tickDelta ->
            render(ctx)
        }
    }

    //#if MC <= 1.19.4
    private fun render(ctx: MatrixStack) {
    //#else
    //$$ private fun render(ctx: DrawContext) {
    //#endif
        val hasDebugInfo =
            //#if MC <= 1.20.1
            MinecraftClient.getInstance().options.debugEnabled
            //#else
            //$$ MinecraftClient.getInstance().debugHud.shouldShowDebugHud()
            //#endif

        if (DaFlight.config.hud && !DaFlight.isGameInactive() && !hasDebugInfo) {
            val x = 5f
            var y = 5f

            if (MovementHandler.isFlying()) {
                //#if MC <= 1.19.4
                textRenderer.draw(ctx, "flying" + if (MovementHandler.isFlyBoosting()) "+" else "", x, y, 0xFFFFFF)
                //#else
                //$$ textRenderer.draw("flying" + if (MovementHandler.isFlyBoosting()) "+" else "", x, y, 0xFFFFFF, true, ctx.matrices.peek().positionMatrix, ctx.vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0xFFFFFF, 0xFFFFFF)
                //#endif

                y += 9
            }

            if (MovementHandler.isSprinting()) {
                //#if MC <= 1.19.4
                textRenderer.draw(ctx, "sprinting" + if (MovementHandler.isSprintBoosting()) "+" else "", x, y, 0xFFFFFF)
                //#else
                //$$ textRenderer.draw("sprinting" + if (MovementHandler.isSprintBoosting()) "+" else "", x, y, 0xFFFFFF, true, ctx.matrices.peek().positionMatrix, ctx.vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0xFFFFFF, 0xFFFFFF)
                //#endif
            }
        }
    }
}
