package io.github.jwdeveloper.spigot.fluent.core.injector.implementation.events;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.ContainerEvents;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.EventHandler;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnInjectionEvent;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnRegistrationEvent;

import java.util.List;

public class EventHandlerImpl implements EventHandler {
    private final List<ContainerEvents> events;

    public EventHandlerImpl(List<ContainerEvents> events)
    {
       this.events = events;
    }

    @Override
    public boolean OnRegistration(OnRegistrationEvent event)
    {
        for(var handler : events)
        {
            var res =handler.OnRegistration(event);
            if(!res)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object OnInjection(OnInjectionEvent event) throws Exception {
        for(var handler : events)
        {
            var obj = handler.OnInjection(event);
            if(obj != event.result())
            {
                return obj;
            }
        }
        return event.result();
    }
}
