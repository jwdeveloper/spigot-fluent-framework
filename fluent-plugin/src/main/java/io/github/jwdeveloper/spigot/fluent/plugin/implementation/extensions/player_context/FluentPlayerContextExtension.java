package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.FluentContainer;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation.FluentPlayerContext;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation.FluentPlayerContextListener;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.api.PlayerContainerBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.api.PlayerContext;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation.PlayerContainerBuilderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPlayerContextExtension implements FluentApiExtension {

    public Consumer<PlayerContainerBuilder>  options;
    public List<RegistrationInfo> registrationInfos;
    private final SimpleLogger logger;

    public FluentPlayerContextExtension(SimpleLogger logger)
    {
        this.logger =logger;
        this.options = (e)->{};
        this.registrationInfos = new ArrayList<>();
    }

    public FluentPlayerContextExtension(Consumer<PlayerContainerBuilder> options, SimpleLogger logger)
    {
        this.options = options;
        this.logger = logger;
        this.registrationInfos = new ArrayList<>();
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container().configure(containerConfiguration ->
        {
           containerConfiguration.onRegistration(onRegistrationEvent ->
           {
               if(!onRegistrationEvent.injectionInfo().hasAnnotation(PlayerContext.class))
               {
                   return true;
               }
               registrationInfos.add(onRegistrationEvent.registrationInfo());
               return false;
           });
        });
        builder.container().register(FluentPlayerContext.class, LifeTime.SINGLETON,(e)->
                {

                    var playerContainerBuilder = new PlayerContainerBuilderImpl(logger);
                    options.accept(playerContainerBuilder);
                    var registrations=  playerContainerBuilder.getConfiguration().getRegistrations();
                    registrationInfos.addAll(registrations);
                    var listener = new FluentPlayerContextListener(builder.plugin());
                    return new FluentPlayerContext((FluentContainer) e, registrationInfos, listener, builder.logger());
                });
    }
}
