package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.documentation;

import io.github.jwdeveloper.spigot.fluent.core.documentation.api.DocumentationDecorator;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentationOptions
{
    private String path;

    private boolean renderCommandsDocumentation =true;

    private boolean renderPermissionsDocumentation =true;

    private boolean useGithubDocumentation =false;

    private boolean useSpigotDocumentation =false;

    private Class<?> permissionTemplate;

    private List<DocumentationDecorator> decorators = new ArrayList<>();

    public void addSection(DocumentationDecorator decorator)
    {
        decorators.add(decorator);
    }
}
