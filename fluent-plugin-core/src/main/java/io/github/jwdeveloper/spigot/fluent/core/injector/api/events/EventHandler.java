package io.github.jwdeveloper.spigot.fluent.core.injector.api.events;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnInjectionEvent;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.events.events.OnRegistrationEvent;
public interface EventHandler extends ContainerEvents
{
    public boolean OnRegistration(OnRegistrationEvent event);
    public Object OnInjection(OnInjectionEvent event) throws Exception;
}
