package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extentions;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.ExtensionModel;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.ExtentionPiority;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.extention.ExtentionModel;
import jw.fluent.plugin.api.extention.ExtentionPiority;
import jw.fluent.plugin.api.extention.FluentApiExtensionsManager;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtensionsManager {
    private final Collection<ExtensionModel> extentions = new ConcurrentLinkedDeque<>();

    @Override
    public void register(FluentApiExtension extension) {
        register(extension, ExtentionPiority.MEDIUM);
    }

    @Override
    public void register(FluentApiExtension extention, ExtentionPiority piority) {
        extentions.add(new ExtensionModel(extention,piority));
    }

    @Override
    public void registerLow(FluentApiExtension extention) {
        register(extention, ExtentionPiority.LOW);
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        //FluentLogger.LOGGER.success("onConfiguration");
        for(var extention : extentions)
        {
           // FluentLogger.LOGGER.log("Piority",extention.getPiority().name(),extention.getExtention().getClass().getSimpleName());
            extention.getExtention().onConfiguration(builder);
        }
    }

    @Override
    public void onEnable(FluentApiSpigot fluentAPI) {
        try
        {
            var sorted = sortByPiority();
            for(var extension : sorted)
            {
                extension.getExtention().onFluentApiEnable(fluentAPI);
            }
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("onFluentApiEnable Fluent API Extension exception",e);
        }
    }

    @Override
    public void onDisable(FluentApiSpigot fluentAPI) {
        var sorted = sortByPiority();
        for(var extention : sorted)
        {
            try
            {
                extention.getExtention().onFluentApiDisabled(fluentAPI);
            }
            catch (Exception e)
            {
                FluentLogger.LOGGER.error("disable error",e);
            }
        }
    }



    private List<ExtensionModel> sortByPiority()
    {
        return extentions.stream().toList().stream().sorted(Comparator.comparing(item -> item.getPiority().getLevel())).toList();
    }
}
