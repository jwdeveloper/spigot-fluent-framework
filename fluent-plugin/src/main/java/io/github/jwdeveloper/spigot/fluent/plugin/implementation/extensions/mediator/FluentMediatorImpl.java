package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.mediator;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.mediator.api.MediatorHandler;
import io.github.jwdeveloper.spigot.fluent.core.mediator.implementation.SimpleMediator;
import java.util.Collection;
import java.util.function.Function;

public class FluentMediatorImpl implements FluentMediator
{
    private final SimpleMediator simpleMediator;
    public FluentMediatorImpl(Collection<Class<?>> mediators,
                              Function<Class<?>,Object> serviceResolver,
                              SimpleLogger logger)
    {
        simpleMediator = new SimpleMediator(serviceResolver, logger);
        for(var mediator : mediators)
        {
            simpleMediator.register((Class<MediatorHandler<?, ?>>)mediator);
        }
    }

    public <Output> Output resolve(Object input, Class<Output> outputClass) {
        return simpleMediator.resolve(input, outputClass);
    }
}
