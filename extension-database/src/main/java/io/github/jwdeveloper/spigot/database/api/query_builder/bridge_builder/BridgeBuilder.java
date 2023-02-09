package io.github.jwdeveloper.spigot.database.api.query_builder.bridge_builder;

import io.github.jwdeveloper.spigot.database.api.query_abstract.AbstractQuery;
import io.github.jwdeveloper.spigot.database.api.query_builder.group_builder.GroupBuilder;
import io.github.jwdeveloper.spigot.database.api.query_builder.join_builder.JoinBuilder;
import io.github.jwdeveloper.spigot.database.api.query_builder.order_builder.OrderBuilder;
import io.github.jwdeveloper.spigot.database.api.query_builder.where_builders.WhereBuilderBridge;
import io.github.jwdeveloper.spigot.database.api.query_builder.where_builders.WhereBuilder;

public interface BridgeBuilder extends WhereBuilderBridge, AbstractQuery
{

    public JoinBuilder join();

    public WhereBuilder where();

    public GroupBuilder groupBy();

    public OrderBuilder orderBy();

}