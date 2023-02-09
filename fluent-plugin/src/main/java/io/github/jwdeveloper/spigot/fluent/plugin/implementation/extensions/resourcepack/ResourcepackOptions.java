package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.resourcepack;

import lombok.Data;

@Data
public class ResourcepackOptions
{
    private String name;

    private String defaultUrl;

    private boolean loadOnJoin;
}
