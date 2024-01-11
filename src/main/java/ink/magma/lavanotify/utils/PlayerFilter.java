package ink.magma.lavanotify.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import ink.magma.lavanotify.LavaNotify;
import ink.magma.lavanotify.error.NoSuchServerException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PlayerFilter {

    @NotNull
    public static Collection<Player> getAll() {
        return LavaNotify.server.getAllPlayers();
    }

    /**
     * 获取某个服务器中的玩家，如果服务器不存在，则为空
     */
    @NotNull
    public static Collection<Player> getInServer(String serverId) throws NoSuchServerException {
        Optional<RegisteredServer> serverOptional = LavaNotify.server.getServer(serverId);
        if (serverOptional.isEmpty()) {
            throw new NoSuchServerException();
        }
        RegisteredServer server = serverOptional.get();
        return server.getPlayersConnected();
    }

    @NotNull
    public static Collection<Player> getInServer(RegisteredServer server) {
        return server.getPlayersConnected();
    }

    /**
     * 使用权限过滤玩家
     */
    @NotNull
    public static Collection<Player> filterPlayersWithPermission(@NotNull Collection<Player> players, @NotNull String permission) {
        Collection<Player> filteredPlayers = new ArrayList<>();

        players.forEach(player -> {
            if (player.hasPermission(permission)) {
                filteredPlayers.add(player);
            }
        });

        return filteredPlayers;
    }

    /**
     * 使用所在服务器过滤玩家
     */
    @NotNull
    public static Collection<Player> filterPlayersWithServer(@NotNull Collection<Player> players, @NotNull String serverId) {
        Collection<Player> filteredPlayers = new ArrayList<>();

        players.forEach(player -> {
            Optional<ServerConnection> currentServer = player.getCurrentServer();
            if (currentServer.isPresent() && currentServer.get().getServerInfo().getName().equals(serverId)) {
                filteredPlayers.add(player);
            }
        });

        return filteredPlayers;
    }
}
