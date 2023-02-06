package io.github.jwdeveloper.spigot.fluent.core.injector.implementation.containers;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.FluentContainer;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.EventHandler;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.factory.InjectionInfoFactory;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.provider.InstanceProvider;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.search.SearchAgent;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;

import java.util.List;

public class FluentContainerImpl extends DefaultContainer implements FluentContainer {

    public FluentContainerImpl(
            SearchAgent searchAgent,
            InstanceProvider instaneProvider,
            EventHandler eventHandler,
            SimpleLogger logger,
            InjectionInfoFactory injectionInfoFactory,
            List<RegistrationInfo> registrationInfos) {
        super(searchAgent, instaneProvider, eventHandler, logger, injectionInfoFactory, registrationInfos);
    }

}
