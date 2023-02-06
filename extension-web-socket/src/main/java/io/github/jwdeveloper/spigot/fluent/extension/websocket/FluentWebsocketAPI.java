package io.github.jwdeveloper.spigot.websocket.extension;

import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.websocket.extension.api.WebsocketOptions;
import io.github.jwdeveloper.spigot.websocket.extension.implementation.WebsocketExtension;

import java.util.function.Consumer;

public class FluentWebsocketAPI
{
    public static FluentApiExtension use(Consumer<WebsocketOptions> options)
    {
        return new WebsocketExtension(options);
    }

    public static FluentApiExtension use()
    {
        return new WebsocketExtension((x)->{});
    }
}
