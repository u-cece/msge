package pet.cece;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.geysermc.mcprotocollib.auth.SessionService;
import org.geysermc.mcprotocollib.network.Server;
import org.geysermc.mcprotocollib.network.event.server.ServerAdapter;
import org.geysermc.mcprotocollib.network.server.NetworkServer;
import org.geysermc.mcprotocollib.protocol.MinecraftConstants;
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol;
import org.geysermc.mcprotocollib.protocol.codec.MinecraftCodec;
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.GameMode;
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.PlayerSpawnInfo;
import org.geysermc.mcprotocollib.protocol.data.status.PlayerInfo;
import org.geysermc.mcprotocollib.protocol.data.status.ServerStatusInfo;
import org.geysermc.mcprotocollib.protocol.data.status.VersionInfo;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class MSGEServer {

    private static final Logger LOGGER = LoggerFactory.getLogger("server");

    private final String hostname;
    private final short port;

    @Nullable
    private Server server;

    public static class Builder {
        private String hostname;
        private short port;

        public Builder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder port(short port) {
            this.port = port;
            return this;
        }

        public MSGEServer build() {
            return new MSGEServer(hostname, port);
        }
    }

    private MSGEServer(String hostname, short port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void start() {
        // listener sending packet event to modify registry packet

        SessionService sessionService = new SessionService();
        sessionService.setProxy(null);

        server = new NetworkServer(new InetSocketAddress(hostname, port), MinecraftProtocol::new);
        server.setGlobalFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        server.setGlobalFlag(MinecraftConstants.ENCRYPT_CONNECTION, true);
        server.setGlobalFlag(MinecraftConstants.SHOULD_AUTHENTICATE, true);
        server.setGlobalFlag(MinecraftConstants.SERVER_INFO_BUILDER_KEY, _ -> new ServerStatusInfo(
                Component.text("meow meow land"),
                new PlayerInfo(69, 420, new ArrayList<>()),
                new VersionInfo(MinecraftCodec.CODEC.getMinecraftVersion(), MinecraftCodec.CODEC.getProtocolVersion()),
                null,
                true
        ));

        server.setGlobalFlag(MinecraftConstants.SERVER_LOGIN_HANDLER_KEY, session ->
                session.send(
                        new ClientboundLoginPacket(
                                0,
                                false,
                                new Key[]{
                                        Key.key("minecraft:overworld"),
                                        Key.key("minecraft:overworld_caves"),
                                        Key.key("minecraft:the_end"),
                                        Key.key("minecraft:the_nether")
                                },
                                0,
                                16,
                                16,
                                false,
                                false,
                                false,
                                new PlayerSpawnInfo(
                                        0,
                                        Key.key("minecraft:overworld"),
                                        100,
                                        GameMode.SURVIVAL,
                                        GameMode.SURVIVAL,
                                        false,
                                        false,
                                        null,
                                        100,
                                        5
                                ),
                                true
                        )
                )
        );

        server.setGlobalFlag(MinecraftConstants.SERVER_COMPRESSION_THRESHOLD, 256);
        server.addListener(new ServerAdapter() {

        });

        server.bind();
    }

    public boolean isRunning() {
        return server != null && server.isListening();
    }
}
