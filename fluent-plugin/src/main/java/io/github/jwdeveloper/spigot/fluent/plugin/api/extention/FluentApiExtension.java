package io.github.jwdeveloper.spigot.fluent.plugin.api.extention;

import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;

public interface FluentApiExtension {

    void onConfiguration(FluentApiSpigotBuilder builder);

    default void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
    }

    default void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
    }

    default ExtentionPiority getPiority() {
        return ExtentionPiority.HIGH;
    }
}
