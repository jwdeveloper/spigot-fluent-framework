package io.github.jwdeveloper.spigot.database.mysql;


import io.github.jwdeveloper.spigot.database.mysql.factories.SqlConnectionFactory;
import io.github.jwdeveloper.spigot.database.mysql.factories.SqlDbContextFactory;
import io.github.jwdeveloper.spigot.database.mysql.models.SqlConnection;
import io.github.jwdeveloper.spigot.database.mysql.models.SqlDbContext;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;

import java.sql.Connection;
import java.sql.SQLException;

public class FluentSqlExtension<T> implements FluentApiExtension {
    private final Class contextType;
    private final SqlConnection connectionDto;
    private Connection connection;
    private SqlDbContext context;

    public FluentSqlExtension(Class<T> contextType, SqlConnection connectionDto) {
        this.contextType = contextType;
        this.connectionDto = connectionDto;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.container().register(SqlDbContext.class, LifeTime.SINGLETON, (x) ->
        {
            context = SqlDbContextFactory.getDbContext(contextType);
            return context;
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var conn = new SqlConnectionFactory().getConnection(connectionDto);
        if (conn.isEmpty()) {
            throw new Exception("Can not establish connection");
        }
        connection = conn.get();
        context.setConnection(connection);
        for (var table : context.tables) {
            var sqlTable = (SqlTable) table;
            sqlTable.createTable();
        }
          /*var table = (SqlTable<Player>)context.tables.get(0);
        var player = table.select()
                .columns("Name","Age","Gender")
                .where().isEqual("Age",2)
                .and().isEqual("Name","Adam")
                .toList();*/
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws SQLException {
        if (connection == null) {
            return;
        }
        connection.close();
    }
}
