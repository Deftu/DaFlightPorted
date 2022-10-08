package xyz.deftu.daflight.utils

import net.minecraft.client.option.KeyBinding

class ToggleKeyBinding(
    name: String,
    category: String,
    code: Int
) : KeyBinding(
    name,
    code,
    category
) {
    private var toggle = false

    fun isToggled(): Boolean {
        return if (isPressed) {
            toggle = true
            false
        } else if (toggle) {
            toggle = false
            true
        } else false
    }
}
