package io.github.jwdeveloper.spigot.fluent.plugin.api.extention;

import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiExtension;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtentionModel
{
    private FluentApiExtension extension;

    private ExtentionPiority priority;

}
