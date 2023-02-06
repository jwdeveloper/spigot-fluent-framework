package io.github.jwdeveloper.spigot.fluent.core.files.json.execution;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import io.github.jwdeveloper.spigot.fluent.core.files.json.annotations.JsonIgnore;

public class JsonIgnoreAnnotationSkip implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(JsonIgnore.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}