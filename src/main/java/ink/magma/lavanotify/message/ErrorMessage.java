package ink.magma.lavanotify.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ErrorMessage {
    public static Component noSuchServer() {
        return Component.text("Cannot find this server!").color(NamedTextColor.RED);
    }

    public static Component cannotParseTitle() {
        return Component.text("Cannot parse title. The title should have at least one line.").color(NamedTextColor.RED);
    }

}
