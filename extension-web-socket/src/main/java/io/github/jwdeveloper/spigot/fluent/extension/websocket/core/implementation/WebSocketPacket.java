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

package io.github.jwdeveloper.spigot.websocket.core.implementation;


import com.google.gson.Gson;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.files.json.JsonUtility;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.Observer;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.TaskManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.spigot.websocket.core.api.FluentWebsocketPacket;
import io.github.jwdeveloper.spigot.websocket.core.api.annotations.PacketProperty;
import io.github.jwdeveloper.spigot.websocket.core.api.resolver.*;
import org.java_websocket.WebSocket;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public abstract class WebSocketPacket implements FluentWebsocketPacket {
    private int packetSize = 0;
    private final int packetIdSize = 4;
    private final List<PacketFieldWrapper> packetFields;
    private final Queue<Consumer<WebSocket>> tasks = new LinkedBlockingQueue<>();
    private final SimpleTaskTimer taskTimer;
    private final Gson gson;

    public abstract void onPacketTriggered(WebSocket webSocket);

    public abstract int getPacketId();

    public WebSocketPacket(TaskManager manager) {
        gson = JsonUtility.getGson();
        packetFields = loadPacketFields();
        packetSize = getPacketSize();
        taskTimer = manager.taskTimer(1, (iteration, taskTimer) ->
        {
            for (final var task : tasks) {
                task.accept(null);
            }
            tasks.clear();
        });
        taskTimer.run();
    }

    protected void addSpigotTask(Consumer<WebSocket> consumer) {
        tasks.add(consumer);
    }

    private List<PacketFieldWrapper> loadPacketFields() {
        var fields = this.getClass().getDeclaredFields();
        final List<PacketFieldWrapper> packetFields = new ArrayList(fields.length);
        for (Field field : fields) {
            if (field.getAnnotation(PacketProperty.class) == null)
                continue;
            var observer = new Observer(this, field);
            TypeResolver typeResolver = null;
            var type = observer.getValueType();
            if (type.getTypeName().equals("int")) {
                typeResolver = new IntResolver();
            }
            if (type.getTypeName().equals("byte")) {
                typeResolver = new ByteResolver();
            }
            if (type.getTypeName().equals("long")) {
                typeResolver = new LongResolver();
            }
            if (type.getTypeName().equals("bool")) {
                typeResolver = new BoolResolver();
            }
            if (type.getTypeName().equals("java.util.UUID")) {
                typeResolver = new UuidResolver();
            }
            if (type.getTypeName().equals("string")) {
                typeResolver = new StringResolver();
            }
            packetFields.add(new PacketFieldWrapper(observer, typeResolver));
        }
        return packetFields;
    }

    public boolean resolvePacket(ByteBuffer buffer) {
        if (packetSize != buffer.limit()) {
            FluentLogger.LOGGER.warning(getClass().getSimpleName(), "Invalid incoming packet size", buffer.limit(), "expected size", packetSize);
            return false;
        }
        int currentIndex = packetIdSize;
        Object result;
        try {
            for (var packetField : packetFields) {
                result = packetField.getResolver().resolve(currentIndex, buffer);
                currentIndex += packetField.getResolver().typeSize();
                packetField.getObserver().set(result);
            }
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Packet resolver error " + this.getClass().getSimpleName(), e);
            return false;
        }
        return true;
    }

    private int getPacketSize() {
        int size = 0;
        for (var packetField : packetFields) {
            size += packetField.getResolver().typeSize();
        }
        return packetIdSize + size;
    }

    protected void sendJson(WebSocket webSocket, Object obj) {
        webSocket.send(gson.toJson(obj));
    }


}
