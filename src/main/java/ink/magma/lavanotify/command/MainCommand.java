package ink.magma.lavanotify.command;

import com.velocitypowered.api.command.CommandSource;
import ink.magma.lavanotify.error.CannotParseTitleException;
import ink.magma.lavanotify.error.NoSuchServerException;
import ink.magma.lavanotify.message.ErrorMessage;
import ink.magma.lavanotify.message.SuccessMessage;
import ink.magma.lavanotify.utils.FeedbackManager;
import ink.magma.lavanotify.utils.Messenger;
import ink.magma.lavanotify.utils.PlayerFilter;
import net.kyori.adventure.text.Component;
import revxrsal.commands.annotation.*;
import revxrsal.commands.velocity.annotation.CommandPermission;

@Command("notify")
@CommandPermission("lavanotify.send")
public class MainCommand {
    @Subcommand("chat all")
    public void chatAll(CommandSource sender, String message) {
        Messenger.sendChat(PlayerFilter.getAll(), message);

        FeedbackManager.toConsole("Chat", "All", message);
        sender.sendMessage(SuccessMessage.sendSuccess());
    }

    @Subcommand("chat server")
    public void chatServer(CommandSource sender, @Single String serverName, String message) {
        try {
            Messenger.sendChat(PlayerFilter.getInServer(serverName), message);

            FeedbackManager.toConsole("Chat", "Server " + serverName, message);
            sender.sendMessage(SuccessMessage.sendSuccess());
        } catch (NoSuchServerException e) {
            sender.sendMessage(ErrorMessage.noSuchServer());
        }
    }


    @Command({"notify broadcast all", "alert", "broadcast", "bc"})
    public void broadcastAll(CommandSource sender, String message) {
        Messenger.sendChat(PlayerFilter.getAll(), appendBroadcastPrefix(message));

        FeedbackManager.toConsole("Chat", "All", appendBroadcastPrefix(message));
        sender.sendMessage(SuccessMessage.sendSuccess());
    }

    @Subcommand("broadcast server")
    public void broadcastServer(CommandSource sender, @Single String serverName, String message) {
        try {
            Messenger.sendChat(PlayerFilter.getInServer(serverName), appendBroadcastPrefix(message));

            FeedbackManager.toConsole("Chat", "Server " + serverName, appendBroadcastPrefix(message));
            sender.sendMessage(SuccessMessage.sendSuccess());
        } catch (NoSuchServerException e) {
            sender.sendMessage(ErrorMessage.noSuchServer());
        }
    }

    private static Component appendBroadcastPrefix(String message) {
        Component prefix = Messenger.autoParse("<gray><</gray><white><bold>!</bold></white><gray>></gray> ");
        Component userMessage = Messenger.autoParse(message);

        return prefix.append(userMessage);
    }

    @Subcommand("actionbar all")
    public void actionBarAll(CommandSource sender, String message) {
        Messenger.sendActionBar(PlayerFilter.getAll(), message);

        FeedbackManager.toConsole("ActionBar", "All", message);
        sender.sendMessage(SuccessMessage.sendSuccess());
    }


    @Subcommand("actionbar server")
    public void actionBarServer(CommandSource sender, @Single String serverName, String message) {
        try {
            Messenger.sendActionBar(PlayerFilter.getInServer(serverName), message);

            FeedbackManager.toConsole("ActionBar", "Server " + serverName, message);
            sender.sendMessage(SuccessMessage.sendSuccess());
        } catch (NoSuchServerException e) {
            sender.sendMessage(ErrorMessage.noSuchServer());
        }
    }

    @Subcommand("title all")
    public void titleAll(
            CommandSource sender, @Named("title") String title, @Named("subtitle") String subtitle,
            @Named("fadeIn") @Default("500") int fadeIn, @Named("stay") @Default("3000") int stay, @Named("fadeOut") @Default("500") int fadeOut
    ) {
        try {
            Messenger.sendTitle(PlayerFilter.getAll(), title, subtitle, fadeIn, stay, fadeOut);

            FeedbackManager.toConsole("Title", "All", title + " " + subtitle);
            sender.sendMessage(SuccessMessage.sendSuccess());
        } catch (CannotParseTitleException e) {
            sender.sendMessage(ErrorMessage.cannotParseTitle());
        }
    }

    @Subcommand("title server")
    public void titleServer(
            CommandSource sender, @Single String serverName,
            @Named("title") String title, @Named("subtitle") String subtitle,
            @Named("fadeIn") @Default("500") int fadeIn, @Named("stay") @Default("3000") int stay, @Named("fadeOut") @Default("500") int fadeOut
    ) {
        try {
            Messenger.sendTitle(PlayerFilter.getInServer(serverName), title, subtitle, fadeIn, stay, fadeOut);

            FeedbackManager.toConsole("Title", "Server " + serverName, title + " " + subtitle);
            sender.sendMessage(SuccessMessage.sendSuccess());
        } catch (CannotParseTitleException e) {
            sender.sendMessage(ErrorMessage.cannotParseTitle());
        } catch (NoSuchServerException e) {
            sender.sendMessage(ErrorMessage.noSuchServer());
        }
    }

    @Subcommand("bossbar all")
    public void bossbarAll(
            CommandSource sender,
            @Named("message") String message
    ) {
        Messenger.sendBossBar(PlayerFilter.getAll(), message);

        FeedbackManager.toConsole("BossBar", "All", message);
        sender.sendMessage(SuccessMessage.sendSuccess());
    }

    @Subcommand("bossbar server")
    public void bossbarServer(CommandSource sender, @Single String serverName, @Named("message") String message) {
        try {
            Messenger.sendBossBar(PlayerFilter.getInServer(serverName), message);

            FeedbackManager.toConsole("BossBar", "Server", message);
            sender.sendMessage(SuccessMessage.sendSuccess());
        } catch (NoSuchServerException e) {
            sender.sendMessage(ErrorMessage.noSuchServer());
        }
    }
}
