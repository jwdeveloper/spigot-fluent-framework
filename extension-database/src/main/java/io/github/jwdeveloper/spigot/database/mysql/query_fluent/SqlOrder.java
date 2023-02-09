package io.github.jwdeveloper.spigot.database.mysql.query_fluent;

import io.github.jwdeveloper.spigot.api.database.api.database_table.models.TableModel;
import io.github.jwdeveloper.spigot.api.database.api.query_builder.order_builder.OrderBuilder;
import io.github.jwdeveloper.spigot.api.database.api.query_fluent.order.OrderFluent;
import io.github.jwdeveloper.spigot.api.database.mysql.query_builder.SqlSyntaxUtils;
import io.github.jwdeveloper.spigot.api.database.mysql.query_builder.order_builder.OrderBuilderImpl;

import java.sql.Connection;

public class SqlOrder<T>  extends SqlQuery<T> implements OrderFluent<T> {

    private final OrderBuilder orderBuilder;

    public SqlOrder(StringBuilder query, Connection connection, TableModel tableModel) {
        super(query, connection, tableModel);
        orderBuilder = new OrderBuilderImpl(query);
    }

    @Override
    public String getQueryClosed() {
        return  orderBuilder.getQuery().concat(SqlSyntaxUtils.SEMI_COL);
    }

    @Override
    public String getQuery() {
        return orderBuilder.getQuery();
    }

    @Override
    public OrderFluent<T> desc(String table) {
        orderBuilder.desc(table);
        return this;
    }

    @Override
    public OrderFluent<T> asc(String table) {
        orderBuilder.asc(table);
        return this;
    }
}
