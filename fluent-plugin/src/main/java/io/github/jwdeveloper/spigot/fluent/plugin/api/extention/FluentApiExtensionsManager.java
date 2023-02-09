package io.github.jwdeveloper.spigot.fluent.plugin.api.extention;


import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;

public interface FluentApiExtensionsManager {
    void register(FluentApiExtension extension);

    void register(FluentApiExtension extension, ExtentionPiority priority);

    void registerLow(FluentApiExtension extension);

    void onEnable(FluentApiSpigot fluentAPI);

    void onDisable(FluentApiSpigot fluentAPI);

    void onConfiguration(FluentApiSpigotBuilder builder);
}
