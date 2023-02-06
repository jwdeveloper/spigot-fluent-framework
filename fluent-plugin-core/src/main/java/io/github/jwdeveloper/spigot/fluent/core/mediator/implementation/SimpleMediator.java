package io.github.jwdeveloper.spigot.fluent.core.mediator.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.common.java.Pair;
import io.github.jwdeveloper.spigot.fluent.core.mediator.api.Mediator;
import io.github.jwdeveloper.spigot.fluent.core.mediator.api.MediatorHandler;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SimpleMediator implements Mediator {
    private static final String MEDIATOR_CLASS_NAME = MediatorHandler.class.getTypeName();
    private final Map<Pair, Class> handlers;
    private final Function<Class<?>,Object> serviceResolver;
    private final SimpleLogger logger;

    public SimpleMediator(Function<Class<?>,Object> serviceResolver, SimpleLogger logger)
    {
        handlers = new HashMap<>();
        this.serviceResolver = serviceResolver;
        this.logger = logger;
    }

    @Override
    public <Output> Output resolve(Object input, Class<Output> outputClass) {
        if (input == null)
            return null;

        var inputClass = input.getClass();
        var pair = new Pair<>(inputClass, outputClass);
        var mediatorClass = handlers.get(pair);
        var mediatorImpl = (MediatorHandler)serviceResolver.apply(mediatorClass);
        if (mediatorClass == null)
        {
            logger.info(String.format(Messages.MEDIATOR_NOT_REGISTERED, inputClass.getSimpleName()));
            return null;
        }
        try {
            return (Output) mediatorImpl.handle(input);
        } catch (Exception e)
        {
            logger.error("Error while executing mediator " + mediatorClass.getSimpleName(), e);
            return null;
        }
    }

    @Override
    public <T extends MediatorHandler<?, ?>> void register(Class<T> mediator) {
        ParameterizedType mediatorInterface = null;
        for (var _interface : mediator.getGenericInterfaces()) {
            var name = _interface.getTypeName();
            if (name.contains(MEDIATOR_CLASS_NAME)) {
                mediatorInterface = (ParameterizedType) _interface;
                break;
            }
        }
        if (mediatorInterface == null)
            return;

        var inputClass = (Class<?>) mediatorInterface.getActualTypeArguments()[0];
        var outputClass = (Class<?>) mediatorInterface.getActualTypeArguments()[1];
        if (handlers.containsKey(inputClass))
        {
            var registerOutput = handlers.get(inputClass);
            logger.info(String.format(Messages.MEDIATOR_ALREADY_REGISTERED, inputClass, registerOutput));
            return;
        }
        handlers.put(new Pair(inputClass,outputClass), mediator);
    }


    @Override
    public boolean containsClass(Class clazz) {
        return handlers.containsKey(clazz);
    }

    public Map<Pair, Class> getRegisteredTypes() {
        return handlers;
    }
}
