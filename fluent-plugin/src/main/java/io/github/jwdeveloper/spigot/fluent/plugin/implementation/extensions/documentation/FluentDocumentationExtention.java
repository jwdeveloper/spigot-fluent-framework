package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.documentation;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.DocumentationDecorator;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.DocumentationRenderer;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.models.Documentation;
import io.github.jwdeveloper.spigot.fluent.core.documentation.implementation.renderer.GithubDocumentationRenderer;
import io.github.jwdeveloper.spigot.fluent.core.documentation.implementation.renderer.PluginDocumentationRenderer;
import io.github.jwdeveloper.spigot.fluent.core.documentation.implementation.renderer.SpigotDocumentationRenderer;
import io.github.jwdeveloper.spigot.fluent.core.files.FileUtility;
import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionDto;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.decorator.CommandsDocumentationDecorator;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.decorator.ConfigDocumentationDecorator;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.decorator.PermissionDocumentationDecorator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentDocumentationExtention implements FluentApiExtension {

    private Consumer<DocumentationOptions> options;

    public FluentDocumentationExtention(Consumer<DocumentationOptions> options) {
        this.options = options;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var model = new DocumentationOptions();
        options.accept(model);
        var decorators = getDecorator(model, fluentAPI.permission().getPermissions());
        var documentation = new Documentation();
        for (var decorator : decorators) {
            decorator.decorate(documentation);
        }
        var renderers = getRenderers(model);
        var basePath = StringUtils.isNullOrEmpty(model.getPath()) ? FluentApi.path() : model.getPath();
        for (var renderer : renderers) {
            var result = renderer.render(new TextBuilder(), documentation);
            var path = basePath + FileUtility.separator() + renderer.getName();
            save(result, path);
        }
    }

    private List<DocumentationDecorator> getDecorator(DocumentationOptions options, List<PermissionModel> permissionModels) {
        var result = new ArrayList<DocumentationDecorator>();
        result.addAll(options.getDecorators());

        var config = new ConfigDocumentationDecorator();
        result.add(config);

        var commandDocumentation = new CommandsDocumentationDecorator(FluentCommand.getManager().getRegisteredCommands());
        result.add(commandDocumentation);

        var permissionDocumentation = new PermissionDocumentationDecorator(new PermissionDto(options.getPermissionTemplate(), permissionModels));
        result.add(permissionDocumentation);
        return result;
    }

    private List<DocumentationRenderer> getRenderers(DocumentationOptions options) {
        var result = new ArrayList<DocumentationRenderer>();
        var spigotDocumentationRenderer = new SpigotDocumentationRenderer();
        var pluginDocumentationRenderer = new PluginDocumentationRenderer();
        var githubRenderer = new GithubDocumentationRenderer();

        if(options.isUseGithubDocumentation())
        {
            result.add(githubRenderer);
        }
        if(options.isUseSpigotDocumentation())
        {
            result.add(spigotDocumentationRenderer);
        }

        result.add(pluginDocumentationRenderer);
        return result;
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }

    public static void save(String content, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(content);
        writer.close();
    }
}
