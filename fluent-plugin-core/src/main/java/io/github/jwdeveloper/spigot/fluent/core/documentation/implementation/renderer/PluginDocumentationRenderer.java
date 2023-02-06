package io.github.jwdeveloper.spigot.fluent.core.documentation.implementation.renderer;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.DocumentationRenderer;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.models.DocumentationSection;

public class PluginDocumentationRenderer  extends DocumentationRenderer {

    @Override
    public String getName() {
        return "documentation.yml";
    }



    @Override
    protected void onTitleSection(TextBuilder builder, DocumentationSection section) {
        builder.newLine();
        builder.text("#").bar("=", 100).newLine();
        builder.text("#").bar(" ", 50).text(" ").text(section.getContent()).text(" ").bar(" ", 50).newLine();
        builder.text("#").bar("=", 100).newLine();
    }

    @Override
    protected void onYmlSection(TextBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("plugin-ignore"))
        {
            return;
        }
        super.onYmlSection(builder, section);
    }

    @Override
    protected void onImageSection(TextBuilder builder, DocumentationSection section) {

    }

    @Override
    protected void onCodeSection(TextBuilder builder, DocumentationSection section) {

    }

    @Override
    protected void onTextSection(TextBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("plugin-ignore"))
        {
            return;
        }

        if(section.hasAttribute("bold"))
        {
            builder.text("#").space().text(section.getContent().toUpperCase()).newLine();
            return;
        }

        builder.text("#").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onListSection(TextBuilder builder, DocumentationSection section) {
        builder.text("#").space(3).text("-").text(section.getContent()).newLine();
    }

    @Override
    protected void onLinkSection(TextBuilder builder, DocumentationSection section) {
        builder.text("#").space(3).text(section.getId()).text(":").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onVideoSection(TextBuilder builder, DocumentationSection section) {
        builder.text("#").space(3).text("video:").space().text(section.getContent()).newLine();
    }
}
