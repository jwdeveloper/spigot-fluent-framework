package jw.fluent.api.database.mysql.factories;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent.api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent.api.database.mysql.models.SqlDbContext;
import jw.fluent.api.database.mysql.models.SqlTable;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;
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
