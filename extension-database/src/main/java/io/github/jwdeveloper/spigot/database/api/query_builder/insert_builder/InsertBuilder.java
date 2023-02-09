package io.github.jwdeveloper.spigot.database.api.query_builder.insert_builder;

public interface InsertBuilder {

    InsertBuilderColumn table(Class<?> tableClass);

    InsertBuilderColumn table(String table);
}
