package io.github.jwdeveloper.spigot.fluent.core.files.yaml.api.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface YamlFile {

    String fileName() default "";

    String globalPath() default "";
}
