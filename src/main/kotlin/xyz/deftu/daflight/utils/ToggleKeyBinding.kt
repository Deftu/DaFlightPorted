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
        if (isPressed) {
            toggle = true
            return false
        } else if (toggle) {
            toggle = false
            return true
        } else return false
    }
}
