package io.github.jwdeveloper.spigot.database.api.query_abstract.order;

import io.github.jwdeveloper.spigot.database.api.query_abstract.AbstractQuery;

public interface AbstractOrderQuery<T> extends AbstractQuery
{
     T desc(String table);

     T asc(String table);
}
