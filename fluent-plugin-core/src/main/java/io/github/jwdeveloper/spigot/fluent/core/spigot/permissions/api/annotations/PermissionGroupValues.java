package io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PermissionGroupValues {
    PermissionGroup[] value();
}