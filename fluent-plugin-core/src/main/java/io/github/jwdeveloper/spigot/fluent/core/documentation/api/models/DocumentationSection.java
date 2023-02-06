package io.github.jwdeveloper.spigot.fluent.core.documentation.api.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class DocumentationSection
{
    private final String id;

    private final SectionType sectionType;

    private final String content;

    private final List<String> attributes;

    public DocumentationSection(String id, SectionType sectionType, String content) {
        this.id = id;
        this.sectionType = sectionType;
        this.content = content;
        attributes = new ArrayList<>();
    }

    public DocumentationSection(String id, SectionType sectionType, String content, List<String> arrtibutes) {
        this.id = id;
        this.sectionType = sectionType;
        this.content = content;
        this.attributes = arrtibutes;
    }

    public boolean hasAttribute(String attribute)
    {
        for(var att : attributes)
        {
            if(att.equals(attribute))
            {
                return true;
            }
        }
        return false;
    }
}
