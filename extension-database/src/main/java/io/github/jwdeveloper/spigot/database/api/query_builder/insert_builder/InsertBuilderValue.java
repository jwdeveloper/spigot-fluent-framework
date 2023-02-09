package io.github.jwdeveloper.spigot.database.api.query_builder.insert_builder;

import io.github.jwdeveloper.spigot.database.api.query_abstract.AbstractQuery;

public interface InsertBuilderValue extends AbstractQuery
{
    public InsertBuilderValue values(Object... values);
}
