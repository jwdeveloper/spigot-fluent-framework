package io.github.jwdeveloper.spigot.fluent.core.translator.api;

import io.github.jwdeveloper.spigot.fluent.core.translator.api.models.LangData;

import java.util.List;

public interface FluentTranslator
{
    String get(String key);

    List<LangData> getLanguages();

    List<String> getLanguagesName();

    boolean setLanguage(String name);

    boolean langAvaliable(String name);

    void generateEmptyTranlations();
}
