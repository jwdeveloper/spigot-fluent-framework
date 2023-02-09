package io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers.builder;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.builders.ContainerBuilder;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.builders.ContainerBuilderConfiguration;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.RegistrationType;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.ContainerConfiguration;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.search.ContainerSearch;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers.DefaultContainer;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.events.EventHandlerImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.factory.InjectionInfoFactoryImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.provider.InstanceProviderImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.search.SearchAgentImpl;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class ContainerBuilderImpl<Builder extends ContainerBuilder<Builder>> implements ContainerBuilder<Builder>, ContainerBuilderConfiguration {
    protected final ContainerConfiguration config;

    public ContainerBuilderImpl() {
        config = new ContainerConfiguration();
    }

    public ContainerConfiguration getConfiguration() {
        return config;
    }

    @SneakyThrows
    public Builder register(Class<?> implementation, LifeTime lifeTime) {
        config.addRegistration(new RegistrationInfo(
                null,
                implementation,
                null,
                lifeTime,
                RegistrationType.OnlyImpl
        ));
        addRegisterdType(implementation);
        return builder();
    }

    @SneakyThrows
    public <T> Builder register(Class<T> _interface, Class<? extends T> implementation, LifeTime lifeTime) {
        config.addRegistration(new RegistrationInfo(
                _interface,
                implementation,
                null,
                lifeTime,
                RegistrationType.InterfaceAndIml
        ));
        addRegisterdType(_interface);
        return builder();
    }

    @SneakyThrows
    @Override
    public <T> Builder registerList(Class<T> _interface, LifeTime lifeTime) {
        config.addRegistration(new RegistrationInfo(
                _interface,
                null,
                (x) ->
                {
                    var container = (ContainerSearch) x;
                    var instances = container.findAllByInterface(_interface);
                    return new ArrayList(instances);
                },
                lifeTime,
                RegistrationType.List
        ));
        addRegisterdType(_interface);
        return builder();
    }

    @SneakyThrows
    @Override
    public <T> Builder registerList(Class<T> _interface, LifeTime lifeTime, Function<Container, Object> provider) {
        config.addRegistration(new RegistrationInfo(
                _interface,
                null,
                provider,
                lifeTime,
                RegistrationType.List
        ));
        addRegisterdType(_interface);
        return builder();
    }

    @Override
    public Builder registerSingletonList(Class<?> _interface) {
        return registerList(_interface, LifeTime.SINGLETON);
    }

    @Override
    public Builder registerTransientList(Class<?> _interface) {
        return registerList(_interface, LifeTime.TRANSIENT);
    }

    @SneakyThrows
    @Override
    public <T> Builder register(Class<T> _interface, LifeTime lifeTime, Function<Container, Object> provider) {
        config.addRegistration(new RegistrationInfo(
                _interface,
                null,
                provider,
                lifeTime,
                RegistrationType.InterfaceAndProvider
        ));
        addRegisterdType(_interface);
        return builder();
    }

    private void addRegisterdType(Class<?> type) throws Exception {
        if (config.getRegisterdTypes().contains(type)) {
            throw new Exception("Type " + type.getSimpleName() + " has been already registerd to container");
        }
        config.getRegisterdTypes().add(type);
    }

    public <T> Builder registerSigleton(Class<T> _interface, Class<? extends T> implementation) {
        return register(_interface, implementation, LifeTime.SINGLETON);
    }


    public <T> Builder registerTransient(Class<T> _interface, Class<? extends T> implementation) {
        return register(_interface, implementation, LifeTime.TRANSIENT);
    }

    public Builder registerSigleton(Class<?> implementation) {
        return register(implementation, LifeTime.SINGLETON);
    }

    public Builder registerTransient(Class<?> implementation) {
        return register(implementation, LifeTime.TRANSIENT);
    }


    public Builder registerSigleton(Class<?> _interface, Object instance) {
        return register(_interface, LifeTime.SINGLETON, (x) ->
        {
            return instance;
        });
    }

    @Override
    public Builder registerSigleton(Class<?> _interface, Function<Container, Object> provider) {
        return register(_interface, LifeTime.SINGLETON, provider);
    }

    public Builder registerTrasient(Class<?> _interface, Object instance) {
        return register(_interface, LifeTime.TRANSIENT, (x) ->
        {
            return instance;
        });
    }

    @Override
    public Builder configure(Consumer<ContainerConfiguration> configuration) {
        configuration.accept(config);
        return builder();
    }

    private Builder builder() {
        return (Builder) this;
    }

    public Container build() throws Exception {
        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new InstanceProviderImpl();
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var searchAgent = new SearchAgentImpl();

        return new DefaultContainer(
                searchAgent,
                instanceProvider,
                eventHandler,
                new SimpleLogger(Logger.getLogger("container")),
                injectionInfoFactory,
                config.getRegistrations());
    }
}