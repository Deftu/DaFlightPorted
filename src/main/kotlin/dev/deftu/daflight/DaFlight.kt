package dev.deftu.daflight

import me.shedaniel.autoconfig.AutoConfig
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import dev.deftu.daflight.handlers.HudHandler
import dev.deftu.daflight.handlers.InputHandler
import dev.deftu.daflight.handlers.PacketHandler
import net.minecraft.util.Identifier

object DaFlight : ClientModInitializer {
    const val NAME = "@MOD_NAME@"
    const val ID = "@MOD_ID@"

    private var initialized = false

    @JvmStatic
    val config: DaFlightConfig
        get() {
            if (!initialized)
                throw IllegalStateException("Calling config too early!")

            return AutoConfig.getConfigHolder(DaFlightConfig::class.java).config
        }

    @JvmStatic
    val player: ClientPlayerEntity?
        get() = MinecraftClient.getInstance().player

    @JvmStatic
    var singleplayerState = false

    @JvmStatic
    var currentServerName = ""

    override fun onInitializeClient() {
        DaFlightConfigBootstrap.register()
        HudHandler.initialize()
        InputHandler.initialize()
        PacketHandler.initialize()

        initialized = true
    }

    @JvmStatic
    fun isPlayerPresent() = player != null

    @JvmStatic
    fun isGameInactive() = MinecraftClient.getInstance().isPaused || !MinecraftClient.getInstance().isWindowFocused

    @JvmStatic
    fun getPlayerForwardMovement() = if (isPlayerPresent()) player!!.input.movementForward else 0f

    @JvmStatic
    fun getPlayerStrafeMovement() = if (isPlayerPresent()) player!!.input.movementSideways else 0f

    fun isFlyingAllowed() = isPlayerPresent() && (singleplayerState || player!!.abilities.allowFlying)

    fun isPlayerInvincible() = player?.isSpectator == true || player?.abilities?.creativeMode == true

    fun setPlayerInvincible(state: Boolean) {
        if (!MinecraftClient.getInstance().isInSingleplayer || isPlayerInvincible()) {
            return
        }

        val clientPlayer = player ?: return
        val server = MinecraftClient.getInstance().server ?: return
        val player = server.playerManager.getPlayer(clientPlayer.uuid)
        player?.abilities?.invulnerable = state
    }

    fun identifier(namespace: String, path: String): Identifier {
        //#if MC >= 1.19.2
        return Identifier.of(namespace, path) ?: throw IllegalArgumentException("Invalid identifier")
        //#else
        //$$ return Identifier(namespace, path)
        //#endif
    }

}
