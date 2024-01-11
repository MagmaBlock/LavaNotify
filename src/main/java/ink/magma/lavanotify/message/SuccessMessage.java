package ink.magma.lavanotify.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SuccessMessage {
    public static Component sendSuccess() {
        return Component.text("Notify has been sent.").color(NamedTextColor.GRAY);
    }
}
