package io.github.jwdeveloper.spigot.database.api.query_builder.update_builder;

import io.github.jwdeveloper.spigot.database.api.query_builder.where_builders.WhereBuilder;
import io.github.jwdeveloper.spigot.database.api.query_builder.where_builders.WhereBuilderBridge;
import io.github.jwdeveloper.spigot.database.api.query_abstract.AbstractQuery;

import java.util.HashMap;

public interface UpdateConditionsQuery extends AbstractQuery, WhereBuilderBridge {

    UpdateConditionsQuery set(String column, Object value);

    UpdateConditionsQuery set(HashMap<String,Object> values);

    WhereBuilder where();
}
