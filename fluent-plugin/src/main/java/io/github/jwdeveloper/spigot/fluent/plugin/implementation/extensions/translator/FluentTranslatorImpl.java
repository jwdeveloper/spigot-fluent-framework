package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.translator;

import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.files.FileUtility;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.models.LangData;
import io.github.jwdeveloper.spigot.fluent.core.translator.implementation.SimpleLang;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApi;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class FluentTranslatorImpl implements FluentTranslator {
    private SimpleLang lang;
    private final SimpleLogger logger;
    private final String path;
    public FluentTranslatorImpl(SimpleLogger logger, String path)
    {
        this.logger = logger;
        this.path = path;
    }


    @Override
    public String get(String key) {
        return lang.get(key);
    }

    @Override
    public List<LangData> getLanguages() {
        return lang.getLanguages();
    }

    @Override
    public List<String> getLanguagesName() {
        return getLanguages().stream().map(c -> c.getCountry()).toList();
    }

    @Override
    public boolean setLanguage(String name) {
        return lang.setLanguage(name);
    }

    @Override
    public boolean langAvaliable(String name) {
        return lang.langExists(name);
    }

    @Override
    @SneakyThrows
    public void generateEmptyTranlations() {
        logger.info("Generating empty tranlations");
        var Optional = getLanguages().stream().filter(f -> f.getCountry().equals("en")).findFirst();
        var eng = Optional.get();


        var filePath = path+ FileUtility.separator()+"temp";
        FileUtility.ensurePath(filePath);
        for(var lang : getLanguages())
        {
            if(eng.getCountry().equals(lang.getCountry()))
            {
                continue;
            }
            FluentLogger.LOGGER.info("========================= Language",lang.getCountry()," =================================");
            FluentLogger.LOGGER.info("Tital paths", lang.getTranslations().size());
            var configuration = new YamlConfiguration();
            for(var yamlPath : eng.getTranslations().keySet())
            {

                var value = StringUtils.EMPTY;
                if(lang.getTranslations().containsKey(yamlPath))
                {
                    FluentLogger.LOGGER.success("Language",lang.getCountry(),yamlPath);
                    value = lang.getTranslations().get(yamlPath);
                }
                else
                {
                    FluentLogger.LOGGER.warning("Language",lang.getCountry(),yamlPath);
                    value = "<!EMPTY!> " +eng.getTranslations().get(yamlPath);
                }
                configuration.set(yamlPath, value);
            }
            var path2 = filePath+ FileUtility.separator()+lang.getCountry()+".yml";
            configuration.save(path2);
        }
        logger.success("Generating done");
    }

    public void setLanguages(List<LangData> language, String name) {
        lang = new SimpleLang(language, logger);
        lang.setDefaultLang("en");
        lang.setLanguage(name);
    }
}
