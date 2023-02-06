package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.CommandManger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.ArgumentConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.EventConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.PropertiesConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.config.SubCommandConfig;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.CommandModel;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.CommandService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.EventsService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.services.MessagesService;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.SimpleCommand;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config.ArgumentConfigImpl;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config.EventConfigImpl;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config.PropertiesConfigImpl;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.builder.config.SubCommandConfigImpl;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.services.CommandServiceImpl;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.services.EventsServiceImpl;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.services.MessageServiceImpl;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommandBuilderImpl implements CommandBuilder {
    protected final EventsService eventsService;
    protected final CommandService commandService;
    protected final MessagesService messagesService;
    protected final List<SimpleCommand> subCommands;

    protected final CommandModel model;
    private final Map<Consumer, BuilderConfig> configs;
    private final Plugin plugin;
    private final CommandManger manger;

    public CommandBuilderImpl(String commandName, Plugin plugin, CommandManger manger) {
        configs = new HashMap<>();
        eventsService = new EventsServiceImpl();
        commandService = new CommandServiceImpl();
        messagesService = new MessageServiceImpl();
        subCommands = new ArrayList<>();
        model = new CommandModel();
        model.setName(commandName);
        this.plugin = plugin;
        this.manger = manger;
    }


    @Override
    public CommandBuilder propertiesConfig(Consumer<PropertiesConfig> config) {
        configs.put(config, new PropertiesConfigImpl(model));
        return this;
    }

    @Override
    public CommandBuilder eventsConfig(Consumer<EventConfig> consumer) {
        configs.put(consumer, new EventConfigImpl(eventsService));
        return this;
    }

    @Override
    public CommandBuilder argumentsConfig(Consumer<ArgumentConfig> config) {
        configs.put(config, new ArgumentConfigImpl(model));
        return this;
    }

    @Override
    public CommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config) {
        configs.put(config, new SubCommandConfigImpl(subCommands, plugin, manger));
        return this;
    }

    @Override
    public SimpleCommand build() {
        for (var configurationSet : configs.entrySet()) {
            configurationSet.getKey().accept(configurationSet.getValue());
        }
        return new SimpleCommand(
                model,
                subCommands,
                commandService,
                messagesService,
                eventsService,
                manger);
    }

    public SimpleCommand buildAndRegister() {
        var result = build();
        result.register();
        return result;
    }
}
