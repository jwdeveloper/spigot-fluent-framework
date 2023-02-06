package io.github.jwdeveloper.spigot.fluent.core.documentation.api;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.models.Documentation;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.models.DocumentationSection;

public abstract class DocumentationRenderer
{
    public abstract String getName();

    protected void onTitleSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onTextSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onImageSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }


    protected void onListSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }

    protected void onCodeSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onYmlSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }
    protected void onHtmlSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }

    protected void onLinkSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }


    protected void onVideoSection(TextBuilder builder, DocumentationSection section)
    {
        addByDefault(builder,section);
    }

    public final String render(TextBuilder builder, Documentation documentation)
    {
        for(var section : documentation.getSections())
        {
            resolveSection(builder, section);
        }
        return builder.toString();
    }

    private void resolveSection(TextBuilder builder, DocumentationSection section)
    {
        switch (section.getSectionType())
        {
            case YML -> onYmlSection(builder, section);
            case CODE-> onCodeSection(builder, section);
            case TEXT-> onTextSection(builder, section);
            case IMAGE-> onImageSection(builder, section);
            case TITlE-> onTitleSection(builder, section);
            case HTML-> onHtmlSection(builder, section);
            case LIST-> onListSection(builder, section);
            case VIDEO-> onVideoSection(builder, section);
            case LINK-> onLinkSection(builder, section);
        }
    }


    private void addByDefault(TextBuilder stringBuilder, DocumentationSection section)
    {
        stringBuilder.text(section.getContent()).newLine();
    }
}
