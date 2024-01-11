package ink.magma.lavanotify.utils;

import com.velocitypowered.api.proxy.Player;
import ink.magma.lavanotify.LavaNotify;
import ink.magma.lavanotify.error.CannotParseTitleException;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Messenger {
    static MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * 字符串消息解析为 Component 对象, 同时支持 MiniMessage 和 legacy 语法 (包括 &#FFFFFF)
     *
     * @param styledMessage 带有样式的字符消息
     * @return Component 消息
     */
    public static @NotNull Component autoParse(@NotNull String styledMessage) {
        return miniMessage.deserialize(legacyToMiniMessage(styledMessage));
    }


    /**
     * 将 legacy 格式转换为 MiniMessage
     *
     * @param styledMessage legacy 输入
     */
    public static @NotNull String legacyToMiniMessage(@NotNull String styledMessage) {
        Map<String, String> legacyToMiniMessageMap = new HashMap<>();
        legacyToMiniMessageMap.put("0", "<black>");
        legacyToMiniMessageMap.put("1", "<dark_blue>");
        legacyToMiniMessageMap.put("2", "<dark_green>");
        legacyToMiniMessageMap.put("3", "<dark_aqua>");
        legacyToMiniMessageMap.put("4", "<dark_red>");
        legacyToMiniMessageMap.put("5", "<dark_purple>");
        legacyToMiniMessageMap.put("6", "<gold>");
        legacyToMiniMessageMap.put("7", "<gray>");
        legacyToMiniMessageMap.put("8", "<dark_gray>");
        legacyToMiniMessageMap.put("9", "<blue>");
        legacyToMiniMessageMap.put("a", "<green>");
        legacyToMiniMessageMap.put("b", "<aqua>");
        legacyToMiniMessageMap.put("c", "<red>");
        legacyToMiniMessageMap.put("d", "<light_purple>");
        legacyToMiniMessageMap.put("e", "<yellow>");
        legacyToMiniMessageMap.put("f", "<white>");
        legacyToMiniMessageMap.put("k", "<obfuscated>");
        legacyToMiniMessageMap.put("l", "<bold>");
        legacyToMiniMessageMap.put("m", "<strikethrough>");
        legacyToMiniMessageMap.put("n", "<underlined>");
        legacyToMiniMessageMap.put("o", "<italic>");
        legacyToMiniMessageMap.put("r", "<reset>");

        var styleChar = '&';

        for (String key : legacyToMiniMessageMap.keySet()) {
            styledMessage = styledMessage.replaceAll(styleChar + key, legacyToMiniMessageMap.get(key));
        }

        styledMessage = styledMessage.replaceAll(styleChar + "#([A-Fa-f0-9]{6})", "<#$1>");
        return styledMessage;
    }

    /**
     * 公告一条消息到目标玩家列表
     *
     * @param message 要发送的消息，不会被二次处理
     */
    public static void sendChat(Collection<Player> players, ComponentLike message) {
        players.forEach(player -> player.sendMessage(message));
    }

    /**
     * 公告一条消息到目标玩家列表
     *
     * @param message 要发送的消息，会被自动解析
     */
    public static void sendChat(Collection<Player> players, String message) {
        sendChat(players, autoParse(message));
    }

    /**
     * 公告一条消息到目标玩家的 ActionBar
     *
     * @param message 要发送的消息，不会被二次处理
     */
    public static void sendActionBar(Collection<Player> players, ComponentLike message) {
        players.forEach(player -> player.sendActionBar(message));
    }

    /**
     * 公告一条消息到目标玩家的 ActionBar
     *
     * @param message 要发送的消息，会被自动解析
     */
    public static void sendActionBar(Collection<Player> players, String message) {
        sendActionBar(players, autoParse(message));
    }

    /**
     * 公告一条 Title
     *
     * @param players 玩家
     * @param message 消息
     */
    public static void sendTitle(Collection<Player> players, Title message) {
        players.forEach(player -> player.showTitle(message));
    }


    /**
     * 公告一条 Title
     *
     * @param players  玩家
     * @param title    标题 (支持样式)
     * @param subtitle 副标题
     * @param fadeIn   渐入时间 (ms)
     * @param stay     停留时间
     * @param fadeOut  渐出时间
     * @throws CannotParseTitleException 解析失败时掷出
     */
    public static void sendTitle(Collection<Player> players, String title, String subtitle, int fadeIn, int stay, int fadeOut) throws CannotParseTitleException {
        if (title == null && subtitle == null) {
            throw new CannotParseTitleException();
        }

        Component line1 = title != null ? autoParse(title) : Component.empty();
        Component line2 = subtitle != null ? autoParse(subtitle) : Component.empty();

        Title.Times titleTimes = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
        Title titleObject = Title.title(line1, line2, titleTimes);

        sendTitle(players, titleObject);
    }

    /**
     * 显示一条 10 秒的蓝色 BossBar
     */
    public static void sendBossBar(Collection<Player> players, String message) {
        BossBar bossBar = BossBar.bossBar(autoParse(message), 1, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);

        sendBossBar(players, bossBar);
    }

    /**
     * 显示十秒 BossBar
     */
    public static void sendBossBar(Collection<Player> players, BossBar bossBar) {
        players.forEach(player -> player.showBossBar(bossBar));

        LavaNotify.server.getScheduler()
                .buildTask(LavaNotify.instance, () -> {
                    players.forEach(bossBar::removeViewer);
                })
                .delay(Duration.ofSeconds(10))
                .schedule();
    }

    /**
     * 在控制台显示一条消息
     */
    public static void sendConsole(ComponentLike message) {
        LavaNotify.server.getConsoleCommandSource().sendMessage(message);
    }

    /**
     * 在控制台显示一条消息
     */
    public static void sendConsole(String message) {
        sendConsole(autoParse(message));
    }

}
