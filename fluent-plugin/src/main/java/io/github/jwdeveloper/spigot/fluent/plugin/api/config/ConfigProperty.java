package io.github.jwdeveloper.spigot.fluent.plugin.api.config;

public record ConfigProperty<T> (String path, T defaultValue, String ... description) {
}
