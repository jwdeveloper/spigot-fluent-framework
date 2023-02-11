package io.github.jwdeveloper.spigot.fluent.core.observer.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObserverListEvent
{
    private boolean cancelled;
    private String operationType;
    private Object value;


    public static ObserverListEvent create(String name, Object value)
    {
        return new ObserverListEvent(false, name, value);
    }

}
