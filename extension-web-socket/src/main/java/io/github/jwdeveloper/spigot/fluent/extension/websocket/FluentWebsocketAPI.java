package io.github.jwdeveloper.spigot.fluent.extension.websocket;

import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.api.WebsocketOptions;

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
