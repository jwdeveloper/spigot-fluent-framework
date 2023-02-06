package io.github.jwdeveloper.spigot.fluent.core.documentation.api.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Documentation
{
    private String name;

    private List<DocumentationSection> sections = new ArrayList<>();
}
