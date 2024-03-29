package ink.magma.lavanotify.utils;

import net.kyori.adventure.text.Component;

import java.text.MessageFormat;

public class FeedbackManager {

    /**
     * 在控制台格式化打印 notify
     *
     * @param type    消息的类型，如 CHAT
     * @param channel 消息的发送目标，如 ALL
     * @param message 消息
     */
    public static void toConsole(String type, String channel, Component message) {
        Component feedback = Component.text(MessageFormat.format("[{0}] [{1}]", type, channel))
                .appendSpace()
                .append(message);

        Messenger.sendConsole(feedback);
    }


    /**
     * 在控制台格式化打印 notify
     *
     * @param type    消息的类型，如 CHAT
     * @param channel 消息的发送目标，如 ALL
     * @param message 消息
     */
    public static void toConsole(String type, String channel, String message) {
        toConsole(type, channel, Messenger.autoParse(message));
    }
}
