package io.github.jwdeveloper.spigot.fluent.plugin.implementation.updater.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdaterOptions
{
    private boolean allowAutoUpdates;
    private String github;
}
