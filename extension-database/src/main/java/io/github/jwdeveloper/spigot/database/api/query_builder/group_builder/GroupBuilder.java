package io.github.jwdeveloper.spigot.database.api.query_builder.group_builder;

import io.github.jwdeveloper.spigot.database.api.query_builder.order_builder.OrderBuilder;
import io.github.jwdeveloper.spigot.database.api.query_abstract.AbstractQuery;
import io.github.jwdeveloper.spigot.database.api.query_builder.where_builders.WhereBuilder;

public interface GroupBuilder extends AbstractQuery
{
     GroupBuilder groupBy();

     GroupBuilder table(String table);

     WhereBuilder having();

     OrderBuilder orderBy();
}
