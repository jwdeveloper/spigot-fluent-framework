package io.github.jwdeveloper.spigot.fluent.extension.websocket.api;

import io.github.jwdeveloper.spigot.fluent.extension.websocket.core.api.FluentWebsocketPacket;

import java.util.Collection;

public interface FluentWebsocket
{
    String getServerIp();

    int getPort();

    void start();

    void stop() throws InterruptedException;

     void registerPackets(Collection<FluentWebsocketPacket> packets);
}
