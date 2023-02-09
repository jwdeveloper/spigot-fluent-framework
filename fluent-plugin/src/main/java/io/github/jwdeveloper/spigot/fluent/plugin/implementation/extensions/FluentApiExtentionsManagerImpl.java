package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.ExtensionModel;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.ExtentionPiority;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtensionsManager {
    private final Collection<ExtensionModel> extensions;
    private final SimpleLogger logger;

    public FluentApiExtentionsManagerImpl(SimpleLogger logger)
    {
        this.logger = logger;
        extensions = new ConcurrentLinkedDeque<>();
    }


    @Override
    public void register(FluentApiExtension extension) {
        register(extension, ExtentionPiority.MEDIUM);
    }

    @Override
    public void register(FluentApiExtension extention, ExtentionPiority piority) {
        extensions.add(new ExtensionModel(extention,piority));
    }

    @Override
    public void registerLow(FluentApiExtension extention) {
        register(extention, ExtentionPiority.LOW);
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        //FluentLogger.LOGGER.success("onConfiguration");
        for(var extention : extensions)
        {
           // FluentLogger.LOGGER.log("Piority",extention.getPiority().name(),extention.getExtention().getClass().getSimpleName());
            extention.getExtension().onConfiguration(builder);
        }
    }

    @Override
    public void onEnable(FluentApiSpigot fluentAPI) {
        try
        {
            var sorted = sortByPiority();
            for(var extension : sorted)
            {
                extension.getExtension().onFluentApiEnable(fluentAPI);
            }
        }
        catch (Exception e)
        {
            logger.error("onFluentApiEnable Fluent API Extension exception",e);
        }
    }

    @Override
    public void onDisable(FluentApiSpigot fluentAPI) {
        var sorted = sortByPiority();
        for(var extention : sorted)
        {
            try
            {
                extention.getExtension().onFluentApiDisabled(fluentAPI);
            }
            catch (Exception e)
            {
               logger.error("disable error",e);
            }
        }
    }



    private List<ExtensionModel> sortByPiority()
    {
        return extensions.stream().toList().stream().sorted(Comparator.comparing(item -> item.getPriority().getLevel())).toList();
    }
}
