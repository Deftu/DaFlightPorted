package xyz.deftu.daflight.handlers

import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import xyz.deftu.daflight.DaFlight

object PacketHandler {
    private val logger = LoggerFactory.getLogger("${DaFlight.NAME} Packet Handler")

    @JvmStatic
    fun handle(channel: Identifier, buffer: PacketByteBuf) {
        if (channel.namespace != DaFlight.ID)
            return

        when (channel.path) {
            "fly" -> MovementHandler.maxFlySpeed = buffer.readFloat()
            "sprint" -> MovementHandler.maxWalkSpeed = buffer.readFloat()
            else -> logger.warn("Server sent invalid packet! That's... Weird...")
        }
    }
}
