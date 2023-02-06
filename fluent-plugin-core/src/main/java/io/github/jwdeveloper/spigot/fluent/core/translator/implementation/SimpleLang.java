package jw.fluent.api.translator.implementation;

import jw.fluent.api.translator.api.models.LangData;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
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

    public SimpleLang(List<LangData> languages)
    {
        this.languages = languages;
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

        FluentLogger.LOGGER.warning(NOT_FOUND+": "+key);
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
        FluentLogger.LOGGER.warning("Language not found: "+name);
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
        FluentLogger.LOGGER.warning("Language not found: "+name);
        return false;
    }



}
