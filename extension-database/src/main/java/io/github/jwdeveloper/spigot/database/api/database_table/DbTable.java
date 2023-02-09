package io.github.jwdeveloper.spigot.database.api.database_table;

import io.github.jwdeveloper.spigot.database.api.query_fluent.select.SelectFluentBridge;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbTable<T>
{
     SelectFluentBridge<T> select();

     DbEntry<T> update(T entity);

     DbEntry<T> insert(T entity);

     DbEntry<T> delete(T entity);

     void saveChanges() throws SQLException;

     void setConnection(Connection connection);
}
