package xyz.deftu.daflight.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface KeyInputEvent {
    Event<KeyInputEvent> EVENT = EventFactory.createArrayBacked(KeyInputEvent.class, (listeners) -> (key, scancode, action, mod) -> {
        for (KeyInputEvent listener : listeners) {
            listener.onKey(key, scancode, action, mod);
        }
    });

    void onKey(int key, int scancode, int action, int mod);
}
