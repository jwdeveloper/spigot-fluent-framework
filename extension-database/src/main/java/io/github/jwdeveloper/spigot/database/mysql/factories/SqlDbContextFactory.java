package io.github.jwdeveloper.spigot.database.mysql.factories;

import io.github.jwdeveloper.spigot.database.mysql.models.SqlDbContext;
import io.github.jwdeveloper.spigot.database.mysql.models.SqlTable;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.RegistrationType;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.models.RegistrationInfo;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.dependecy_injection.FluentInjectionImpl;
import lombok.SneakyThrows;

import java.lang.reflect.ParameterizedType;

public class SqlDbContextFactory
{
    @SneakyThrows
    public static  <T extends SqlDbContext> T getDbContext(Class<T> contextType)
    {
        var registrationInfo = new RegistrationInfo(null,
                contextType,
                null,
                LifeTime.SINGLETON,
                RegistrationType.OnlyImpl);
        var injection = (FluentInjectionImpl) FluentApi.container();
        injection.getContainer().register(registrationInfo);
        var context = (SqlDbContext)injection.findInjection(contextType);
        for (var field : contextType.getDeclaredFields()) {
            field.setAccessible(true);
            var value = field.get(context);
            if (value != null)
                continue;

            var type = field.getType();
            if (!type.isInterface()) {
                continue;
            }


            var genericType = (ParameterizedType)field.getGenericType();
            var genericTypeArg = genericType.getActualTypeArguments()[0];
            var obj = new SqlTable((Class)genericTypeArg);
            context.tables.add(obj);
            field.set(context,obj);
        }
        return (T)context;
    }
}
