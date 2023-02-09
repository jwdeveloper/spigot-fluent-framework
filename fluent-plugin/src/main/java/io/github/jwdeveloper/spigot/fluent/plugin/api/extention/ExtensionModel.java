package io.github.jwdeveloper.spigot.fluent.plugin.api.extention;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtensionModel
{
    private FluentApiExtension extension;

    private ExtentionPiority priority;
}
