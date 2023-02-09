package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options;

import lombok.Builder;
import lombok.Data;

@Data
public class UpdaterOptions
{
    private boolean forceUpdate;
    private boolean checkUpdateOnStart = true;
    private String commandName = "update";
}
