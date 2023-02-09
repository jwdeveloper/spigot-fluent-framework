package io.github.jwdeveloper.spigot.fluent.extension.websocket;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.core.api.FluentWebsocketPacket;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.core.implementation.WebSocketBase;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.api.FluentWebsocket;
import java.util.Collection;

public class FluentWebsocketImpl extends WebSocketBase implements FluentWebsocket
{
    private String serverIp;

    public void setServerIp(String serverIp)
    {
        this.serverIp = serverIp;
    }

    public String getServerIp()
    {
     return serverIp;
    }

    @Override
    public int getPort() {
        return super.getPort();
    }

    public FluentWebsocketImpl(int port, SimpleLogger logger) {
        super(port, logger);
    }

    @Override
    public void stop() throws InterruptedException {
        super.stop();
    }

    @Override
    public void registerPackets(Collection<FluentWebsocketPacket> packets) {
        super.registerPackets(packets);
    }

    @Override
    public void start() {
        super.start();
    }
}
