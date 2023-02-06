package io.github.jwdeveloper.spigot.fluent.core.files.json.execution;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.Observer;


public class BindingFieldSkip implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes)
    {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass)
    {
        if(aClass.equals(Observer.class))
        {
            return true;
        }
        return false;
    }
}
