package io.github.jwdeveloper.spigot.fluent.core.files.yaml.api.models;

import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.files.json.JsonUtility;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YamlModel
{
    private String fileName = StringUtils.EMPTY;
    private String description = StringUtils.EMPTY;
    private List<YamlContent> contents = new ArrayList<>();

    public void addContent(YamlContent content)
    {
        this.contents.add(content);
    }

    public String toString()
    {
        return JsonUtility.getGson().toJson(this);
    }

    public boolean hasDescription()
    {
        return description.length() != 0;
    }
}
