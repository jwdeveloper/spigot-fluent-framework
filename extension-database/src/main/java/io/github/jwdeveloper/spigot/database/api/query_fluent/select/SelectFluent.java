package io.github.jwdeveloper.spigot.database.api.query_fluent.select;

import io.github.jwdeveloper.spigot.database.api.query_fluent.QueryFluent;
import io.github.jwdeveloper.spigot.database.api.query_fluent.order.OrderFluent;
import io.github.jwdeveloper.spigot.database.api.query_fluent.order.OrderFluentBridge;
import io.github.jwdeveloper.spigot.database.api.query_fluent.where.WhereFluent;
import io.github.jwdeveloper.spigot.database.api.query_abstract.select.AbstractSelectQuery;

public interface SelectFluent<T> extends AbstractSelectQuery<SelectFluent<T>>, OrderFluentBridge<T>, QueryFluent<T>
{
     SelectFluent<T> join(Class<?> foreignTable);

     WhereFluent<T> where();

     OrderFluent<T> orderBy();
}
