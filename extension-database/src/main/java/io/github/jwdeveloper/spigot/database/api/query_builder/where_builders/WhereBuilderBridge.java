package io.github.jwdeveloper.spigot.database.api.query_builder.where_builders;

import io.github.jwdeveloper.spigot.database.api.query_abstract.AbstractQuery;

public interface WhereBuilderBridge extends AbstractQuery
{
    WhereBuilder where();
}