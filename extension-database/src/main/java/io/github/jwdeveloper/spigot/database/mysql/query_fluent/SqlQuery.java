package io.github.jwdeveloper.spigot.database.mysql.query_fluent;

import jw.fluent.api.database.api.database_table.models.ColumnModel;
import jw.fluent.api.database.api.database_table.models.TableModel;
import jw.fluent.api.database.api.query_fluent.QueryFluent;
import jw.fluent.api.database.mysql.query_builder.SqlSyntaxUtils;
import jw.fluent.api.utilites.Stopwatch;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.*;

public abstract class SqlQuery<T> implements QueryFluent<T> {
    protected StringBuilder query;
    protected Connection connection;
    protected TableModel tableModel;
    protected Set<ColumnModel> joinedColumns;
    protected Stopwatch stopper;

    public SqlQuery(StringBuilder query, Connection connection, TableModel tableModel) {
        this.connection = connection;
        this.tableModel = tableModel;
        this.query = query;
        joinedColumns = new HashSet<>();
        stopper = new Stopwatch();
    }

    public SqlQuery(Connection connection, TableModel tableModel) {
        this(new StringBuilder(), connection, tableModel);
    }

    public List<T> toList() {
        var result = executeQuery(getQueryClosed());
        return getResult(result, tableModel);
    }

    public List<T> toList(int amount) {
        var query = getQuery()
                .concat(" LIMIT ")
                .concat(String.valueOf(amount))
                .concat(SqlSyntaxUtils.SEMI_COL);
        var result = executeQuery(query);
        return getResult(result, tableModel);
    }

    public Optional<T> first() {
        var result = toList(1);
        return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
    }

    private List<T> getResult(ResultSet resultSet, TableModel tableModel)
    {
        stopper.start();
        var result = new SqlQueryExecutor<T>(resultSet)
                .setTable(tableModel)
                .setJoins(joinedColumns)
                .toList();
        stopper.stop(" Result mapping");
        return result;
    }

    @SneakyThrows
    private ResultSet executeQuery(String query) {

        var statement = connection.prepareStatement(query);

        stopper.start();
        var result = statement.executeQuery();
        stopper.stop(query);
        return result;
    }

}
