package io.github.jwdeveloper.spigot.fluent.core.translator.api.models;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class LangData
{
    private String country = "undefined";

    private Map<String, String> translations = new ConcurrentHashMap<>();
}
