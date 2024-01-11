package ink.magma.lavanotify;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import ink.magma.lavanotify.command.MainCommand;
import org.slf4j.Logger;
import revxrsal.commands.velocity.VelocityCommandHandler;

@Plugin(
        id = "lava-notify",
        name = "LavaNotify",
        version = "1.1.1",
        description = "All in one Notify manager",
        authors = {"MagmaBlock"}

)
public class LavaNotify {
    public static Logger logger;
    public static ProxyServer server;
    public static LavaNotify instance;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        VelocityCommandHandler handler = VelocityCommandHandler.create(this, server);
        handler.register(new MainCommand());
    }

    @Inject
    public LavaNotify(Logger logger, ProxyServer server) {
        LavaNotify.logger = logger;
        LavaNotify.server = server;
        LavaNotify.instance = this;
    }
}
