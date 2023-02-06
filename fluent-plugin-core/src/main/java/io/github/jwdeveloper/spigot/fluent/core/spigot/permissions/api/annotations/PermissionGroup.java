package io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.annotations;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
@Repeatable(PermissionGroupValues.class)
public @interface PermissionGroup {
    String group() default "";
    String title() default "";
}