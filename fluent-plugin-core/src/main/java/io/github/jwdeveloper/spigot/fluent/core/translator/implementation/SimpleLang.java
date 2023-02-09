package io.github.jwdeveloper.spigot.fluent.core.translator.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.models.LangData;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.List;

@Getter
public class SimpleLang {

    private final String LANGUAGE_NOT_SELECTED = ChatColor.RED+"LANGUAGE NOT SELECTED";
    private final String NOT_FOUND = ChatColor.RED+"TRANSLATION NOT FOUND";
    private final List<LangData> languages;
    private LangData currentLang;
    private LangData defaultLang;

    private final SimpleLogger logger;

    public SimpleLang(List<LangData> languages, SimpleLogger logger)
    {
        this.languages = languages;
        this.logger = logger;
    }

    public String get(String key) {
        if(currentLang == null)
        {
            return LANGUAGE_NOT_SELECTED;
        }

        if (currentLang.getTranslations().containsKey(key)) {
            return currentLang.getTranslations().get(key);
        }

        if (defaultLang.getTranslations().containsKey(key)) {
            return defaultLang.getTranslations().get(key);
        }

        logger.warning(NOT_FOUND+": "+key);
        return NOT_FOUND;
    }


    public boolean setDefaultLang(String name)
    {
        for(var language : languages)
        {
            if(language.getCountry().equals(name))
            {
                defaultLang = language;
                return true;
            }
        }
        logger.warning("Language not found: "+name);
        return false;
    }

    public boolean langExists(String  name)
    {
        return languages.stream().anyMatch(c -> c.getCountry().equals(name));
    }

    public boolean setLanguage(String name)
    {
        for(var language : languages)
        {
            if(language.getCountry().equals(name))
            {
                currentLang = language;
                return true;
            }
        }
        logger.warning("Language not found: "+name);
        return false;
    }



}
