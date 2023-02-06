package io.github.jwdeveloper.spigot.fluent.core.decorator.implementation;

import io.github.jwdeveloper.spigot.fluent.core.decorator.api.Decorator;
import io.github.jwdeveloper.spigot.fluent.core.decorator.api.DecoratorInstanceProvider;
import io.github.jwdeveloper.spigot.fluent.core.decorator.api.models.DecorationDto;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnInjectionEvent;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnRegistrationEvent;

import java.util.Map;

public class DefaultDecorator implements Decorator
{
    private final Map<Class<?>, DecorationDto> decorators;
    private final DecoratorInstanceProvider decoratorInstanceProvider;

    public DefaultDecorator(DecoratorInstanceProvider decoratorInstanceProvider,
                            Map<Class<?>, DecorationDto> decorators) {
        this.decorators = decorators;
        this.decoratorInstanceProvider = decoratorInstanceProvider;
    }

    @Override
    public boolean OnRegistration(OnRegistrationEvent event) {
        return true;
    }

    public Object OnInjection(OnInjectionEvent event) throws Exception {
        var decoratorDto =  decorators.get(event.input());
        if(decoratorDto == null)
        {
            return event.result();
        }
        var result = event.result();
        for(var injectionInfo : decoratorDto.implementations())
        {

            var nextDecorator = decoratorInstanceProvider.getInstance(
                    injectionInfo,
                    event.injectionInfoMap(),
                    result,
                    event.container());
            result = nextDecorator;
        }
        return result;
    }
}
