package io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.EventHandler;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnInjectionEvent;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnRegistrationEvent;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.factory.InjectionInfoFactory;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.InjectionInfo;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.provider.InstanceProvider;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.search.ContainerSearch;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.search.SearchAgent;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.utilites.Messages;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultContainer implements Container, ContainerSearch {
    protected final EventHandler eventHandler;
    protected final InstanceProvider instaneProvider;
    protected final SimpleLogger logger;
    protected final Map<Class<?>, InjectionInfo> injections;
    protected final InjectionInfoFactory injectionInfoFactory;
    protected final SearchAgent searchAgent;

    public DefaultContainer(SearchAgent searchAgent,
                            InstanceProvider instaneProvider,
                            EventHandler eventHandler,
                            SimpleLogger logger,
                            InjectionInfoFactory injectionInfoFactory,
                            List<RegistrationInfo> registrationInfos) {
        this.instaneProvider = instaneProvider;
        this.eventHandler = eventHandler;
        this.logger = logger;
        this.injectionInfoFactory = injectionInfoFactory;
        this.searchAgent = searchAgent;
        this.injections = new HashMap<>();

        for (var registration : registrationInfos) {
            register(registration);
        }
    }

    @Override
    public boolean register(RegistrationInfo registrationInfo) {
        Class<?> clazz = switch (registrationInfo.registrationType()) {
            case InterfaceAndIml, InterfaceAndProvider, List -> registrationInfo._interface();
            case OnlyImpl -> registrationInfo.implementation();
        };
        if (injections.containsKey(clazz)) {
            logger.error(String.format(Messages.INJECTION_ALREADY_EXISTS, clazz.getSimpleName()));
            return false;
        }
        try {
            var pair = injectionInfoFactory.create(registrationInfo);
            var regisrationResult = eventHandler.OnRegistration(new OnRegistrationEvent(pair.key(), pair.value(), registrationInfo));
            if (!regisrationResult) {
                return false;
            }
            injections.put(pair.key(), pair.value());
        } catch (Exception e) {
            logger.error(String.format(Messages.INJECTION_CANT_REGISTER, clazz.getSimpleName()));
            return false;
        }
        return true;
    }

    @SneakyThrows
    @Override
    public Object find(Class<?> _injection) {
        var injectionInfo = injections.get(_injection);
        if (injectionInfo == null) {
            logger.error(String.format(Messages.INJECTION_NOT_FOUND, _injection.getSimpleName()));
            return eventHandler.OnInjection(new OnInjectionEvent(_injection, injectionInfo, null, injections, this));
        }
        try {
            var result = instaneProvider.getInstance(injectionInfo, injections, this);
            return eventHandler.OnInjection(new OnInjectionEvent(_injection, injectionInfo, result, injections, this));
        } catch (Exception e) {
            logger.error(String.format(Messages.INJECTION_CANT_CREATE, _injection.getSimpleName()), e);
            return null;
        }
    }


    @Override
    public <T> Collection<T> findAllByInterface(Class<T> _interface) {
        return searchAgent.<T>findAllByInterface(this::find,injections,_interface);
    }

    @Override
    public <T> Collection<T> findAllBySuperClass(Class<T> superClass) {
        return searchAgent.<T>findAllBySuperClass(this::find,injections,superClass);
    }

    @Override
    public Collection<Object> findAllByAnnotation(Class<? extends Annotation> _annotation) {
        return searchAgent.findAllByAnnotation(this::find,injections,_annotation);
    }
}
