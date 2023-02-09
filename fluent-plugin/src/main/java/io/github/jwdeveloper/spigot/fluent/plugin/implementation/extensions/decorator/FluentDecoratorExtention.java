package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.decorator;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.decorator.api.builder.DecoratorBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;

public class FluentDecoratorExtention implements FluentApiExtension {

    private DecoratorBuilder decoratorBuilder;

    public FluentDecoratorExtention(DecoratorBuilder decoratorBuilder)
    {
        this.decoratorBuilder = decoratorBuilder;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.container().configure(containerConfiguration ->
        {
            try
            {
                var decorator = decoratorBuilder.build();
                containerConfiguration.onEvent(decorator);
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Unable to register decorator ",e);
                return;
            }
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
