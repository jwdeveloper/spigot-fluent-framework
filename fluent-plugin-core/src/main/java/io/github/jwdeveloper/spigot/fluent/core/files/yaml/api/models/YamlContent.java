package io.github.jwdeveloper.spigot.fluent.core.files.yaml.api.models;

import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
public class YamlContent {
    private List<YamlContent> children = new ArrayList<>();
    private String name = StringUtils.EMPTY;
    private String path = StringUtils.EMPTY;

    private String description = StringUtils.EMPTY;

    private Field field;

    private Class<?> clazz;

    private boolean isList;


    public boolean isObject() {
        return children.size() != 0;
    }

    public String getFullPath() {
        if (path.isEmpty()) {
            return name;
        }
        return path + "." + name;
    }
}
