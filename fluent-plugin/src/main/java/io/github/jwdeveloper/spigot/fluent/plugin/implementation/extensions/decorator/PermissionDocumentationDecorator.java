package io.github.jwdeveloper.spigot.fluent.plugin.implementation.decorator;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.DocumentationDecorator;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.builders.YmlBuilder;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.models.Documentation;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionDto;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.implementation.PermissionModelResolver;
import java.util.ArrayList;
import java.util.List;


public class PermissionDocumentationDecorator extends DocumentationDecorator {

    private final PermissionDto permissionGeneratorDto;

    private final PermissionModelResolver resolver;

    private final int defaultOffset = 2;
    private final int propertyOffset = 4;

    public PermissionDocumentationDecorator(PermissionDto permissionGeneratorDto) {
        this.permissionGeneratorDto = permissionGeneratorDto;
        resolver = new PermissionModelResolver();
    }

    @Override
    public void decorate(Documentation documentation) {
        var models = getModels();
        addTitle("Permissions", documentation, "yml-title");
        addImage("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/permissions.png", documentation);
        var builder = createYmlBuilder();
        builder.addSection("permissions");
        builder.newLine();
        renderSections(builder, models);
        var yml = builder.build();
        addYml(yml, documentation);
    }

    private void renderSections(YmlBuilder builder, List<PermissionModel> sections) {
        for (var section : sections) {
            renderSection(builder, section);
            renderSections(builder, section.getChildren());
        }
    }

    private void renderSection(YmlBuilder builder, PermissionModel section) {

        if(section.hasChildren())
        {
            var msg = new TextBuilder();
            msg.bar("=",40);
            msg.space();
            msg.text(section.getFullPath());
            msg.space();
            msg.bar("=",41-section.getFullPath().length());
            builder.addComment(msg.toString());
        }

        builder.addSection(section.getRealFullPath(), defaultOffset);
        var description = section.getDescription();
        if (section.hasDescription()) {
            builder.addProperty("description", description, propertyOffset);
        } else {
            if (!section.hasChildren()) {
                builder.addProperty("description", "default", propertyOffset);
            }
        }
        if (section.hasChildren()) {
            builder.addProperty("description", "full access", propertyOffset);
        }
        builder.newLine();
    }

    private List<PermissionModel> getModels() {
        List<PermissionModel> models = new ArrayList<>();
        try {
            var model = resolver.createModels(permissionGeneratorDto._class());
            models = resolver.merge(model, permissionGeneratorDto.permissionModels());
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to generate permissions", e);
        }
        return models;
    }
}