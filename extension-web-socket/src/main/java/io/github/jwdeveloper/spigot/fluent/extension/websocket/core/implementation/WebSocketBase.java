/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveloper.spigot.fluent.extension.websocket.core.implementation;


import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.core.api.FluentWebsocketPacket;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketBase extends WebSocketServer {
    private final ConcurrentHashMap<Integer, WebSocketPacket> webSocketEvents;
    private final SimpleLogger logger;

    public WebSocketBase(int port, SimpleLogger logger) {
        super(new InetSocketAddress(port));
        webSocketEvents = new ConcurrentHashMap<>();
        this.logger = logger;
    }

    public void registerPackets(Collection<FluentWebsocketPacket> packets)
    {
        for (var packet : packets)
        {
            webSocketEvents.put(packet.getPacketId(), (WebSocketPacket)packet);
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
      //  FluentPlugin.logInfo(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
       // FluentPlugin.logInfo(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " leave the room!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteBuffer buffer) {
        var packetId = buffer.getInt(0);
        var webSocketEvent = webSocketEvents.get(packetId);
        if(webSocketEvent == null)
            return;
        if (!webSocketEvent.resolvePacket(buffer))
            return;
        webSocketEvent.onPacketTriggered(webSocket);
    }

    @Override
    public void onClosing(WebSocket conn, int code, String reason, boolean remote) {
        super.onClosing(conn, code, reason, remote);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if(webSocket == null)
            logger.error("Web socket error ", e);
        else
            logger.error("Web socket error "+webSocket.getRemoteSocketAddress().getAddress().toString(), e);
    }
    @Override
    public void onStart() {
        //FluentPlugin.logSuccess("Hello world from socket");
    }
}
