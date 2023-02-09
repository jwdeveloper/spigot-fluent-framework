package io.github.jwdeveloper.spigot.database.api.query_builder.where_builders;

import io.github.jwdeveloper.spigot.database.api.query_builder.group_builder.GroupBuilder;
import io.github.jwdeveloper.spigot.database.api.query_builder.order_builder.OrderBuilderBridge;
import io.github.jwdeveloper.spigot.database.api.query_abstract.AbstractQuery;
import io.github.jwdeveloper.spigot.database.api.query_abstract.where.AbstractWhereQuery;

public interface WhereBuilder extends AbstractWhereQuery<WhereBuilder>, AbstractQuery, OrderBuilderBridge
{


    public GroupBuilder groupBy();
}