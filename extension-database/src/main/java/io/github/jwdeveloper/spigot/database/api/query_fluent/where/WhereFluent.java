package io.github.jwdeveloper.spigot.database.api.query_fluent.where;

import io.github.jwdeveloper.spigot.database.api.query_abstract.where.AbstractWhereQuery;
import io.github.jwdeveloper.spigot.database.api.query_fluent.QueryFluent;
import io.github.jwdeveloper.spigot.database.api.query_fluent.order.OrderFluentBridge;

public interface WhereFluent<T> extends AbstractWhereQuery<WhereFluent<T>>, OrderFluentBridge<T>, QueryFluent<T> {
}
