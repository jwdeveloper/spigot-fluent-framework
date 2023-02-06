package io.github.jwdeveloper.spigot.fluent.core.injector.implementation.provider;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.InjectionInfo;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.provider.InstanceProvider;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.utilites.Messages;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class InstanceProviderImpl implements InstanceProvider
{
    @Override
    public Object getInstance(InjectionInfo info, Map<Class<?>, InjectionInfo> injections, Container container) throws Exception {
        if (info.getLifeTime() == LifeTime.SINGLETON && info.getInstnace() != null)
            return info.getInstnace();

        Object result = null;
        InjectionInfo handler = null;
        Class<?> parameterClass = null;
        if (info.hasInjectedConstructor()) {
            var i = 0;
            for (var parameterType : info.getConstructorTypes())
            {
                parameterClass = parameterType;
                if(parameterClass.isAssignableFrom(List.class))
                {
                    var parameterizedType = (ParameterizedType)info.getInjectedConstructor().getGenericParameterTypes()[i];
                    parameterClass =  (Class<?>)parameterizedType.getActualTypeArguments()[0];
                }
                if (!injections.containsKey(parameterClass))
                {
                    throw new Exception(String.format(Messages.INJECTION_NOT_FOUND, parameterClass.getTypeName(), info.getInjectionKeyType()));
                }
                handler = injections.get(parameterClass);
                info.getConstructorPayLoadTemp()[i] = getInstance(handler, injections, container);
                i++;
            }
            result = info.getInjectedConstructor().newInstance(info.getConstructorPayLoadTemp());
            info.setInstnace(result);
            return result;
        }

        result = switch (info.getRegistrationInfo().registrationType())
        {
            case InterfaceAndIml, OnlyImpl -> info.getRegistrationInfo().implementation().newInstance();
            case InterfaceAndProvider, List -> info.getRegistrationInfo().provider().apply(container);
        };
        info.setInstnace(result);
        return result;
    }
}
