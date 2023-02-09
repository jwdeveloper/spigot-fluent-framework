package io.github.jwdeveloper.spigot.database.api.query_builder.delete_builder;

import io.github.jwdeveloper.spigot.database.api.query_builder.where_builders.WhereBuilderBridge;

public interface DeleteBuilder 
{
     WhereBuilderBridge from(Class<?> tableClass);

     WhereBuilderBridge from(String table);

}
