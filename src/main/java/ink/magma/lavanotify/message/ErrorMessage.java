package ink.magma.lavanotify.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ErrorMessage {
    public static Component noSuchServer() {
        return Component.text("指定的服务器不存在!").color(NamedTextColor.RED);
    }

    public static Component cannotParseTitle() {
        return Component.text("Title 解析失败, 至少需要有一行! 使用 \\n 来分割多行.").color(NamedTextColor.RED);
    }

}
