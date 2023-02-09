package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.FluentContainer;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.player_context.api.FluentPlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FluentPlayerContext {
    private final ConcurrentHashMap<UUID, Container> playerContainers;
    private final FluentContainer mainContainer;
    private final List<RegistrationInfo> registrationInfos;
    private final FluentPlayerContextListener listener;
    private final SimpleLogger logger;

    public FluentPlayerContext(FluentContainer mainContainer,
                               List<RegistrationInfo> registrationInfos,
                               FluentPlayerContextListener listener,
                               SimpleLogger logger)
    {
        playerContainers = new ConcurrentHashMap<>();
        this.mainContainer = mainContainer;
        this.registrationInfos = registrationInfos;
        this.listener = listener;
        this.logger = logger;
    }

    public <T> T find(Class<T> injectionType, Player player) {
        return find(injectionType, player.getUniqueId());
    }
    public <T> T find(Class<T> injectionType, UUID uuid) {

        if (!playerContainers.containsKey(uuid)) {
            try {
                playerContainers.put(uuid, CreateContainer(uuid));
            } catch (Exception e) {
                logger.error("Unable register container for player " + uuid.toString(), e);
                return null;
            }
        }
        final var container = playerContainers.get(uuid);
        return (T) container.find(injectionType);
    }

    public <T> T clear(Class<T> injectionType, Player player) {
        //TODO this
        return null;
    }

    private Container CreateContainer(UUID uuid) throws Exception {
        return new PlayerContainerBuilderImpl(logger)
                .setParentContainer(mainContainer)
                .configure(containerConfiguration ->
                {
                    containerConfiguration.addRegistration(registrationInfos);
                })
                .register(FluentPlayer.class, LifeTime.SINGLETON, container ->
                {
                    var fluentPlayer = new FluentPlayerImpl(uuid);
                    listener.register(fluentPlayer);
                    return fluentPlayer;
                })
                .build();
    }
}
