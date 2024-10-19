package dev.deftu.daflight.utils

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.shedaniel.autoconfig.AutoConfig
import dev.deftu.daflight.DaFlightConfig

class ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory { parent ->
        AutoConfig.getConfigScreen(DaFlightConfig::class.java, parent).get()
    }
}
